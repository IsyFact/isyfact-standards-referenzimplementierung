package de.bund.bva.isyfact.shop.service.rest.api;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Smoketest for checking get-product requests:
 * - sends HTTP requests to RestApplication and
 * - asserts expected response
 * <p>
 * Note: RestApplication will automatically be started and listening on a random port!
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProduktControllerApiTest {

    @LocalServerPort
    private int serverPort;

    /**
     * Sends get request to the produkte resource
     * of our isifact-standards-tutorial application, running on localhost:8081.
     */
    @Test
    void testGetProduktBoByIdRequest() {

        // given
        WebClient client = WebClient.create();

        // when
        Mono<ProduktBo> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(serverPort)
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
        Mono<List<ProduktBo>> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(serverPort)
                        .path("/shop/api/v1/produkte")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProduktBo>>() {});

        List<ProduktBo> produktBoList = response.block();

        // then
        assertNotNull(produktBoList);
        assertEquals(3, produktBoList.size());
    }
}


