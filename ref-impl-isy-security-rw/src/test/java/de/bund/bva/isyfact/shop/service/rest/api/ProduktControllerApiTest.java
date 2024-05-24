package de.bund.bva.isyfact.shop.service.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Smoketest for checking get-product requests.
 */
public class ProduktControllerApiTest extends ApiTest{

    /**
     * Sends get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    public void testGetProduktBoByIdRequest() {

        // given
        WebClient client = WebClient.create();

        // when
        Mono<ProduktBo> response = client.get()
                .uri("http://localhost:8081/shop/api/v1/produkte/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProduktBo.class);

        ProduktBo produktBo = response.block();

        // then
        assertEquals("{ id: 1, name:'Emmentaler', beschreibung:'Hartkäse' }",
                produktBo.toString());
    }

    /**
     * Sends another get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    public void testGetAllProduktBoRequest() {

        // given
        WebClient client = WebClient.create();

        // when
        Mono<List> response = client.get()
                .uri("http://localhost:8081/shop/api/v1/produkte")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class);

        List<ProduktBo> produktBoList = (List<ProduktBo>) response.block();

        // then
        assertNotNull(produktBoList);
        assertEquals(3,produktBoList.size());
    }

    @Test
    public void testPutProduktBoRequest() throws JsonProcessingException {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(1,"Allgäuer Emmentaler","Hartkäse");

        String clientAId = "client-a";                 // see key cloak ...
        String clientASecret = "piPYyzamZYat6USdEls15OTOJdAFU60v";           // see key cloak ...

        String token = initializeTokenForClient(clientAId, clientASecret);

        // when
        ProduktBo result = updateProduktBo(modifiedProduktBo,"http://localhost:8081/shop/api/v1/produkte" , token);

        // then
        assertEquals(1L, result.getId());
    }

}


