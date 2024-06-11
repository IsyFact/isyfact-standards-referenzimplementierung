package de.bund.bva.isyfact.shop.core.impl;

import de.bund.bva.isyfact.shop.core.daten.ProduktBo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * This class provides a method for updating a given product
 * that is managed by an external REST application running under localhost:8081.
 */
@Service
public class AwfProdukteAktualisieren {

    /**
     * This constructor injects the required dependencies.
     * @param webClient provided by a preconfigured bean:
     *                  see {@link de.bund.bva.isyfact.shop.service.rest.configuration.WebClientSecurityConfig}
     */
    public AwfProdukteAktualisieren(WebClient webClient) {
        this.webClient = webClient;
    }
    private final WebClient webClient;

    /**
     * Updates the properties of the given product
     * this method uses the above webClient to call 'Update Produkt' of RestApplicationRW in module ref-impl-security-rw.
     * @param produktBo Produkt business object with new attributes to be updated
     * @return the updated Produkt business object, as in database
     */
    public ProduktBo updateProduktBo(ProduktBo produktBo) {

        String url = "localhost:8081/shop/api/v1/produkte/";

        Mono<ProduktBo> response = webClient.put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(produktBo), ProduktBo.class)
                .retrieve()
                .bodyToMono(ProduktBo.class);

        return response.block();
    }
}
