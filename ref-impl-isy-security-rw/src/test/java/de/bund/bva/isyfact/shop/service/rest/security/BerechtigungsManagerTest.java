package de.bund.bva.isyfact.shop.service.rest.security;

import de.bund.bva.isyfact.security.core.Security;
import de.bund.bva.isyfact.security.oauth2.client.Authentifizierungsmanager;
import de.bund.bva.isyfact.shop.RestApplicationRW;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.ProduktController;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


@SpringBootTest(classes= RestApplicationRW.class)
class BerechtigungsManagerTest extends AbstractResourceTest {

    /**
     * Demonstration of a custom attribute check (for e.g. a fine-grained authorization)
     * Use case: Search for Produkt business objects with a given name.
     *      * If no such name is passed:
     *      * - For users in department (Abteilung) 'Zentrale':
     *      *   all Product business objects are returned, without any restriction.
     *      * - For all other users:
     *      *   ProduktNotFoundException
     * <p>
     * Note: Needs a configured IAM running
     **/

    @Autowired
    private Security security;

    @Autowired
    // public resource: ProduktController is configured as public & not secured
            ProduktController produktController;


    /**
     * Call without authentication: ProduktNotFoundException expected
     */
    @Test
    void testCustomAuthorizationResourceWithoutAuthentication() {

        // given
        // no Authentication = no user attribute 'abteilung'
        assertNull(getAuthentication());

        // when
        // call 'findAllProduktBo' without specifying a product name as search parameter
        // then
        // expect exception 'Produkt not found'
        assertThrows(ProduktNotFoundException.class,
                () -> produktController.findAllProduktBo(null));
    }

    /**
     * Call with user NOT in 'Zentrale': ProduktNotFoundException expected
     */
    @Test
    void testCustomAuthorizationResourceWithWrongAuthentication() {

        // given
        // confidential client auth data, as defined in KeyCloak:

        // and an authenticated user having the required role / right but without user attribute 'abteilung' = 'Zentrale'
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                    "user-b", "test");
        } else {
            fail("Authenticationmanager is null");
        }
        // SecurityContext contains new token
        assertNotNull(getAuthentication());

        // token contains user attribute 'abteilung' having value 'Zentrale'
        assertNull(security.getBerechtigungsmanager().getTokenAttribute("abteilung"),
                "user-b does have user attribute 'abteilung'");

        // when
        // call 'findAllProduktBo' without specifying a product name as search parameter
        // then
        // expect exception 'Produkt not found'
        assertThrows(ProduktNotFoundException.class,
                () -> produktController.findAllProduktBo(null));
    }

    /**
     * Call with user in 'Zentrale': OK-response expected
     */
    @Test
    void testCustomAuthorizationResourceWithCorrectAuthentication() throws ProduktNotFoundException {

        // given
        // confidential client auth data, as defined in KeyCloak:

        // and an authenticated user having the required role / right and user attribute 'abteilung' = 'Zentrale'
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                    "user-a", "test");
        } else {
            fail("Authenticationmanager is null");
        }
        // SecurityContext contains new token
        assertNotNull(getAuthentication());

        // - Actual Isyfact privileges DO include 'PRIV_Recht_A' (as role-a is assigned to user-a in IAM)
        assertTrue(security.getBerechtigungsmanager().getRechte().contains("PRIV_Recht_A"),
                "user-a does NOT have privilege 'Recht-A'");

        // - Actual Isyfact privileges DO include 'PRIV_Recht_A' (as role-a is assigned to user-a in IAM)
        assertTrue(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "user-a does NOT have privilege 'Recht-A'");

        // - Actual Isyfact privileges DO include 'PRIV_Recht_A' (as role-a is assigned to user-a in IAM)
        try {
            security.getBerechtigungsmanager().pruefeRecht("PRIV_Recht_A");
        } catch (AccessDeniedException e) {
            fail("user-a does NOT have privilege 'Recht-A'");
        }

        // - Actual Isyfact roles DO include 'role-a' (as role-a is assigned to user-a in IAM)
        assertTrue(security.getBerechtigungsmanager().getRollen().contains("role-a"),
                "user-a does NOT have role 'role-a'");

        // token contains user attribute 'abteilung' having value 'Zentrale'
        String abteilung = (String) security.getBerechtigungsmanager().getTokenAttribute("abteilung");
        assertNotNull(abteilung, "user-a does NOT have user attribute 'abteilung'");
        assertEquals("Zentrale", abteilung, "user-a does NOT have user attribute 'Abteilung: Zentrale'");

        // when
        // call 'findAllProduktBo' without specifying a product name as search parameter
        ResponseEntity<List<ProduktBo>> responseEntity = produktController.findAllProduktBo(null);
        List<ProduktBo> produktBoList = responseEntity.getBody();

        // then
        // OK-response expected
        assertNotNull(produktBoList);
    }

}
