package de.bund.bva.isyfact.shop.service.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoketest for checking product requests:
 * - sends HTTP requests to RestApplicationRW and
 * - asserts expected response
 * <p>
 * Note: RestApplicationRW has to be started manually and will be listening on port 8081
 */
class ProduktControllerApiTest extends ApiTest{

    /**
     * Sends get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    void testGetProduktBoByIdRequest() {

        // given
        WebClient client = WebClient.create();

        // when
        // sending request and receiving its response
        Mono<ProduktBo> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/shop/api/v1/produkte/1")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProduktBo.class);

        ProduktBo produktBo = response.block();

        // then
        assertNotNull(produktBo, "ProduktBo should not be null");
        assertEquals(1, produktBo.getId());
        assertEquals("Emmentaler", produktBo.getName());
        assertEquals("Hartkäse", produktBo.getBeschreibung());
    }

    /**
     * Sends another get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    void testGetAllProduktBoRequest() {

        // given
        WebClient client = WebClient.create();

        // when
        // sending request and receiving its response
        Mono<List<ProduktBo>> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/shop/api/v1/produkte")
                        .queryParam("name", "Emmentaler")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProduktBo>>() {});

        List<ProduktBo> produktBoList = response.block();

        // then
        assertNotNull(produktBoList);
        assertEquals(1, produktBoList.size());

        ProduktBo expectedProduktBo = new ProduktBo(1, "Emmentaler", "Hartkäse");
        ProduktBo actualProduktBo = produktBoList.get(0);

        assertEquals(expectedProduktBo.getId(), actualProduktBo.getId());
        assertEquals(expectedProduktBo.getName(), actualProduktBo.getName());
        assertEquals(expectedProduktBo.getBeschreibung(), actualProduktBo.getBeschreibung());
    }

    @Test
    void testPutProduktBoRequest() throws JsonProcessingException {

        // given
        ProduktBo modifiedProduktBo = new ProduktBo(4,"Gouda","Schnittkäse");

        String clientAId = "client-a";                                  // see key cloak ...
        String clientASecret = "piPYyzamZYat6USdEls15OTOJdAFU60v";      // see key cloak ...

        String token = initializeTokenForClient(clientAId, clientASecret);

        WebClient client = WebClient.create();

        // when
        // sending request and receiving its response
        Mono<ProduktBo> response = client.put()
                 .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/shop/api/v1/produkte")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .body(Mono.just(modifiedProduktBo), ProduktBo.class)
                .retrieve()
                .bodyToMono(ProduktBo.class);

        ProduktBo result = response.block();

        // then
        assertEquals(4L, result.getId());
        assertEquals("Gouda", result.getName());
    }
}


