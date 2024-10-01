package de.bund.bva.isyfact.shop.service.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This Api test is an integration test. It demonstrates that within a batch application,
 * running on localhost:8082 we can call secured endpoints running on a different host localhost:8081.
 * For performing this call it is necessary
 * - that we authenticate ourselves as registered client (A) with role-A and PRIV_Recht_A.
 * - and to have a mechanism that extracts the required token from the current security context
 * - and to put this token into the header of the outgoing request.
 *
 *  Note: RestApplicationW and RestApplicationRW need to be started manually and will be listening then
 *  on ports 8081 and 8082.
 *
 */
class ProduktControllerApiTest extends ApiTest {

    @Test
    void testPutProduktBoRequest() throws JsonProcessingException {

        // given
        // a client having the required role / right to trigger a task
        // but not the role / right required by external the endpoint to be called in this task
        String clientBId = "client-b";                                  // see key cloak ...
        String clientBSecret = "htXMYwbXuxftL1x5gblwy1ysbDysLnKB";      // see key cloak ...

        // and a modified product
        ProduktBo modifiedProduktBo = new ProduktBo(4,"Gouda","Schnittkäse");

        // and a token for this client
        String token = initializeTokenForClient(clientBId, clientBSecret);

        // when
        // sending request and receiving its response
        WebClient client = WebClient.create();

        // ProduktBo result = updateProduktBo(modifiedProduktBo,"http://localhost:8082/shop/api/v1/produkte" , token);

        Mono<ProduktBo> response = client.put()
                //.uri("http://localhost:8082/shop/api/v1/produkte")
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("/shop/api/v1/produkte")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(modifiedProduktBo), ProduktBo.class)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ProduktBo.class);
        ProduktBo result = response.block();

        // then
        assertEquals(4L, result.getId());
        assertEquals("Gouda", result.getName());
    }
/*
    protected static ProduktBo updateProduktBo(ProduktBo produktBo, String url, String token) {

        WebClient client = WebClient.create();

        Mono<ProduktBo> response = client.put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(produktBo), ProduktBo.class)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ProduktBo.class);

        return response.block();
    }
 */
}
