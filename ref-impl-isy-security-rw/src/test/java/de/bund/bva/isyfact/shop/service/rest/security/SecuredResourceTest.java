package de.bund.bva.isyfact.shop.service.rest.security;

import de.bund.bva.isyfact.security.core.Berechtigungsmanager;
import de.bund.bva.isyfact.security.core.Security;
import de.bund.bva.isyfact.security.oauth2.client.Authentifizierungsmanager;
import de.bund.bva.isyfact.shop.RestApplicationRW;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.ProduktController;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;


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
public class SecuredResourceTest extends AbstractResourceTest {

    @Autowired
    // secured resource: produktController
    ProduktController produktController;

    @Autowired
    private Security security;

    /**
     * Call without authentication: AuthenticationCredentialsNotFoundException expected
     */
    @Test
    public void testAccessingSecuredResourceWithoutAuthentication() {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        // no Authentication
        Assertions.assertNull(getAuthentication());


        // when
        // calling a secured endpoint method, requiring special rights
        // then
        // expect exception 'Authentication-Credentials not found'
        Assertions.assertThrows(AuthenticationCredentialsNotFoundException.class,
                () -> produktController.updateProduktBo(modifiedProduktBo));
    }

    /**
     * Call with prior authentication, but wrong authorization: AccessDeniedException expected
     * (using explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager().hatRecht})
     */
    @Test
    public void testAccessingSecuredResourceWithWrongAuthentication() {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        // an authenticated user not having the required role / privilege
        security.getAuthentifizierungsmanager().orElseThrow()
                .authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                        "user-b", "test");

        // SecurityContext contains new token
        Assertions.assertNotNull(getAuthentication());
        // actual rights do NOT include required right
        Assertions.assertFalse(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "user-b does have privilege 'Recht-A'");

        // when
        // calling a secured endpoint method, requiring this right
        // then
        // expect exception 'Access denied'
        Assertions.assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> produktController.updateProduktBo(modifiedProduktBo));
    }

    /**
     * Call with prior authentication & correct authorization: OK-response expected
     * (using explicit client (Client-Credential-Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereClient})
     * (testing with {@link Berechtigungsmanager().hatRecht})
     */
    @Test
    public void testAccessingSecuredResourceWithCorrectAuthentication() throws ProduktNotFoundException {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(4,"alter Gouda","Schnittkäse");

        // an authenticated client having the required role / right
        security.getAuthentifizierungsmanager().orElseThrow()
                .authentifiziereClient(issuerUriA, clientAId, clientASecret);

        // SecurityContext contains new token
        Assertions.assertNotNull(getAuthentication());
        // actual rights DO include required right
        Assertions.assertTrue(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "client-a is missing privilege 'Recht-A'");

        // when
        // calling a secured endpoint method, requiring this right
        ProduktBo updateProduktBo = produktController.updateProduktBo(modifiedProduktBo).getBody();

        // then
        // verify the returned updateProduktBo
        Assertions.assertEquals(4L, updateProduktBo.getId());
        Assertions.assertEquals("alter Gouda", updateProduktBo.getName());
    }

}
