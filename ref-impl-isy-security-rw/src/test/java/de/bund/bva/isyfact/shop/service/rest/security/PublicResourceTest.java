package de.bund.bva.isyfact.shop.service.rest.security;

import de.bund.bva.isyfact.security.core.Security;
import de.bund.bva.isyfact.shop.RestApplicationRW;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import de.bund.bva.isyfact.shop.service.rest.ProduktController;
import de.bund.bva.isyfact.shop.service.rest.configuration.OAuth2ServerSecurityConfig;
import de.bund.bva.isyfact.shop.service.rest.exception.ProduktNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Call of a public resource, not needing authentication:
 * - without prior authentication: OK
 * - with prior authentication: OK
 * <p>
 * This proves,that IsyFact Security does not conflict with a public resource
 * (if it is configured as public as in {@link OAuth2ServerSecurityConfig().filterChain}).
**/


@SpringBootTest(classes= RestApplicationRW.class)
public class PublicResourceTest extends AbstractResourceTest {

    @Autowired
    // public resource: ProduktController is configured as public & not secured
    ProduktController produktController;

    @Autowired
    private Security security;

    /**
     * Call without prior authentication: OK-response expected
     */
    @Test
    public void testPublicResourceWithoutAuthentication() throws ProduktNotFoundException {

        // given
        // no Authentication
        assertNull(getAuthentication());

        // when
        // call public resource
        ResponseEntity<ProduktBo> responseEntity = produktController.findProduktBoById(1);
        ProduktBo produktBo = responseEntity.getBody();

        // then
        // OK-response expected
        assertNotNull(produktBo);
        assertEquals(1, produktBo.getId()); ;
    }

    /**
     * Call with prior authentication: OK-response expected
     */
    @Test
    public void testPublicResourceWithAuthentication() throws ProduktNotFoundException {


        // given
        // confidential client auth data, as defined in KeyCloak:
        //String clientId = "confidential-client";
        //String clientSecret = "RCX1o8KF1Iez3d0XvcKPOJjiDIFnpKay";

        // and an authenticated user having the required role / right
        security.getAuthentifizierungsmanager().orElseThrow()
                .authentifiziereSystem(issuerUriA, confidentialClientId, confidentialClientSecret,
                        "user-a", "test");                      // see key cloak

        // SecurityContext contains new token
        assertNotNull(getAuthentication());

        // when
        // call public resource
        ResponseEntity<ProduktBo> responseEntity = produktController.findProduktBoById(1);
        ProduktBo produktBo = responseEntity.getBody();

        // then
        // OK-response expected
        assertNotNull(produktBo);
        assertEquals(1, produktBo.getId()); ;
    }

}
