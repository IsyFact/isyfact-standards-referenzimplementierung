package de.bund.bva.isyfact.shop.service.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This Api test is an integration test. It demonstrates that within a batch application,
 * running on localhost:8082 we can call secured endpoints running on a different host localhost:8081.
 * For performing this call it is necessary
 * - that we authenticate ourselves as registered client (A) with role-A and PRIV_Recht_A.
 * - and to have a mechnism that extracts the required token from the current security context
 * - and to put this token into the header of the outgoing request.
 */
public class ProduktControllerApiTest extends ApiTest {

    @Test
    public void testPutProduktBoRequest() throws JsonProcessingException {

        // given
        // a client having the required role / right to trigger a task
        // but not the role / right required by external the endpoint to be called in this task
        String clientBId = "client-b";                 // see key cloak ...
        String clientBSecret = "htXMYwbXuxftL1x5gblwy1ysbDysLnKB";           // see key cloak ...

        // and a modified product
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        // and a token for this client
        String token = initializeTokenForClient(clientBId, clientBSecret);

        // when
        // triggering the task
        ProduktBo result = updateProduktBo(modifiedProduktBo,"http://localhost:8082/shop/api/v1/produkte" , token);

        // then
        // authentication for the external service, and executing this service works as expected:
        // it returns the right product
        assertEquals(1L, result.getId());
        assertEquals("Allgäuer Emmentaler",result.getName());
    }
}
