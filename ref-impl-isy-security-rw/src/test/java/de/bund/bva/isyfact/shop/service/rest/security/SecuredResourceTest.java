package de.bund.bva.isyfact.shop.service.rest.security;

import de.bund.bva.isyfact.security.core.Berechtigungsmanager;
import de.bund.bva.isyfact.security.core.Security;
import de.bund.bva.isyfact.security.oauth2.client.Authentifizierungsmanager;
import de.bund.bva.isyfact.shop.RestApplicationRW;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.ProduktController;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Call of a secured resource, needing authentication:
 * - without prior authentication: AuthenticationCredentialsNotFoundException
 * - with prior authentication & wrong authorization: AccessDeniedException
 * - with prior authentication & proper authorization: OK
 * <p>
 *  This proves,that a resource annotated with {@link org.springframework.security.access.annotation.Secured}
 *  can only be accessed with authentication & proper authorization.
**/

@SpringBootTest(classes= RestApplicationRW.class)
class SecuredResourceTest extends AbstractResourceTest {

    @Autowired
    // secured resource: produktController
    ProduktController produktController;

    @Autowired
    private Security security;

    /**
     * Call without authentication: AuthenticationCredentialsNotFoundException expected
     */
    @Test
    void testAccessingSecuredResourceWithoutAuthentication() {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        // no Authentication
        assertNull(getAuthentication());


        // when
        // calling a secured endpoint method, requiring special rights
        // then
        // expect exception 'Authentication-Credentials not found'
        assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> produktController.updateProduktBo(modifiedProduktBo));
    }

    /**
     * Call with prior authentication, but wrong authorization: AccessDeniedException expected
     * (using explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager().hatRecht})
     */
    @Test
    void testAccessingSecuredResourceWithWrongAuthentication() {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        // an authenticated user not having the required role / privilege
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get() .authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                    "user-b", "test");
        } else {
            fail("Authenticationmanager is null");
        }
        // SecurityContext contains new token
        assertNotNull(getAuthentication());
        // actual rights do NOT include required right
        assertFalse(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "user-b does have privilege 'Recht-A'");

        // when
        // calling a secured endpoint method, requiring this right
        // then
        // expect exception 'Access denied'
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> produktController.updateProduktBo(modifiedProduktBo));
    }

    /**
     * Call with prior authentication & correct authorization: OK-response expected
     * (using explicit client (Client-Credential-Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereClient})
     * (testing with {@link Berechtigungsmanager().hatRecht})
     */
    @Test
    void testAccessingSecuredResourceWithCorrectAuthentication() throws ProduktNotFoundException {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(4,"alter Gouda","Schnittkäse");

        // an authenticated client having the required role / right
       Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziereClient(issuerUriA, clientAId, clientASecret);
        } else {
            fail("Authenticationmanager is null");
        }
        // SecurityContext contains new token
        assertNotNull(getAuthentication());
        // actual rights DO include required right
        assertTrue(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "client-a is missing privilege 'Recht-A'");

        // when
        // calling a secured endpoint method, requiring this right
        ProduktBo updateProduktBo = produktController.updateProduktBo(modifiedProduktBo).getBody();

        // then
        // verify the returned updateProduktBo
        assertEquals(4L, updateProduktBo.getId());
        assertEquals("alter Gouda", updateProduktBo.getName());
    }

}
