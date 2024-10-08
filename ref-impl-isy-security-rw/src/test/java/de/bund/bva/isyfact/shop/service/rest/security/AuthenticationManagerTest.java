package de.bund.bva.isyfact.shop.service.rest.security;

import de.bund.bva.isyfact.security.core.Berechtigungsmanager;
import de.bund.bva.isyfact.security.core.Security;
import de.bund.bva.isyfact.security.oauth2.client.Authentifizierungsmanager;
import de.bund.bva.isyfact.shop.RestApplicationRW;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstration of the different ways of authentication:
 * - as explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
 * - as explicit client (Client-Credential-Flow with auth-data as parameters)
 * - as registered client (Client-Credential-Flow with auth-data from application resources)
 * - as registered techn. user (Resource-Owner-Password-Flow with auth-data from application resources)
 * <p>
 *  This shows the different methods of authentication provided by the Authentifizierungsmanager,
 *  the kinds of credentials and their configuration & storage,
 *  and proves, that an access token with the correct roles/rights is placed in the SecurityContext.
 * <p>
 * Note: Needs a configured IAM running
 **/
@SpringBootTest(classes= RestApplicationRW.class)
class AuthenticationManagerTest extends AbstractResourceTest {

    @Autowired
    private Security security;

    /**
     * Authenticate as explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager ().getRollen})
     */
    @Test
    void testAuthenticateAsExplicitTechnUser() {

        // Given:
        // - an explicit techn. user (user-a) with name & password and
        // - a confidential client (confidentialClient) with id & secret and
        // - the URI of the IAM, with realm (issuerUriA)
        // (see ControllerTest)

        // When:
        // Authenticate at IAM
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                    "user-a", "test");
        } else {
            fail("Authenticationmanager is null");
        }

        // Then:
        // - SecurityContext contains new token
        assertNotNull(getAuthentication());
        // - Actual Isyfact roles does include 'role-a' (as assigned to user-a in IAM)

        assertTrue(security.getBerechtigungsmanager().getRollen().contains("role-a"),
                "user-a does NOT have IsyFact role 'role-a'");
    }

    /**
     * Authenticate as explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager ().pruefeRecht})
     */
    @Test
    void testAuthenticateAsExplicitClient() throws Exception {

        // Given:
        // - an explicit client (clientA) with id & secret and
        // - the URI of the IAM, with realm (issuerUriA)
        // (see ControllerTest)

        // When:
        // Authenticate at IAM
       Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziereClient(issuerUriA, clientAId, clientASecret);
        } else {
            fail("Authenticationmanager is null");
        }
        security.getAuthentifizierungsmanager();
        // Then:
        // - SecurityContext contains new token
        assertNotNull(getAuthentication());
        // - Actual Isyfact privileges do NOT include 'PRIV_Recht_B' (as role-b is NOT assigned to clientA in IAM)
        assertThrowsExactly(org.springframework.security.access.AccessDeniedException.class ,
                () -> security.getBerechtigungsmanager().pruefeRecht("PRIV_Recht_B"),
                "clientA DOES have IsyFact privilege 'PRIV_Recht_B'");
    }

    /**
     * Authenticate as explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager ().pruefeRecht})
     */
    @Test
    void testAuthenticateAsRegisteredClient() {

        // Given:
        // - an registered client (reg-client-a),
        // - registration data in application configuration
        //   (application.yml: spring.security.oauth2.client.registration.reg-client-a)

        // When:
        // Authenticate at IAM
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziere("reg-client-a");
        } else {
            fail("Authenticationmanager is null");
        }

        // Then:
        // - SecurityContext contains new token
        assertNotNull(getAuthentication());
        // - Actual Isyfact privileges DO include 'PRIV_Recht_A' (as role-a is assigned to reg-client-a in IAM)
        assertTrue(security.getBerechtigungsmanager().hatRecht("PRIV_Recht_A"),
                "reg-client-a is missing privilege 'Recht-A'");
    }

    /**
     * Authenticate as explicit techn. user (Resource-Owner-Password-Credential Flow with auth-data as parameters)
     *  {@link Authentifizierungsmanager#authentifiziereSystem})
     * (testing with {@link Berechtigungsmanager ().pruefeRecht})
     */
    @Test
    void testAuthenticateAsRegisteredTechnUser() {

        // Given:
        // - an registered techn. user (reg-user-a),
        // - registration data in application configuration
        //   (application.yml: spring.security.oauth2.client.registration.reg-user-a)

        // When:
        // Authenticate at IAM
        Optional<Authentifizierungsmanager> am = security.getAuthentifizierungsmanager();
        if (am.isPresent()) {
            am.get().authentifiziere("reg-user-a");
        } else {
            fail("Authenticationmanager is null");
        }
        // Then:
        // - SecurityContext contains new token
        assertNotNull(getAuthentication());
        // - Actual Isyfact privileges DO include 'PRIV_Recht_A' (as role-a is assigned to user-a in IAM)
        assertTrue(security.getBerechtigungsmanager().getRechte().contains("PRIV_Recht_A"),
                "user-a is missing privilege 'Recht-A'");
    }

}
