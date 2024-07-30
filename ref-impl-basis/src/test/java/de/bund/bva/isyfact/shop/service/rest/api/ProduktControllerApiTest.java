package de.bund.bva.isyfact.shop.service.rest.api;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
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
public class ProduktControllerApiTest {

    @LocalServerPort
    private int serverPort;

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
                .uri("http://localhost:"+ serverPort+ "/shop/api/v1/produkte/1")
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
                .uri("http://localhost:"+ serverPort + "/shop/api/v1/produkte")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(List.class);

        List<ProduktBo> produktBoList = (List<ProduktBo>) response.block();

        // then
        assertNotNull(produktBoList);
        assertEquals(3,produktBoList.size());
    }
}


