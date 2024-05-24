package de.bund.bva.isyfact.shop.service.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This configuration class provides a standard WebClient which already includes a token that is derived
 * from the current security context. This WebClient can then be used for creating and sending REST requests
 * to other REST applications.
 * The token is read from the SecurityContext and put into the Authorization Header of the HTTP-Request.
 * See: IsyFact Standards - Bausteine - Security - Nutzungsvorgaben: 3.3. Tokenweitergabe an Nachbarsysteme.
 */
@Configuration
public class WebClientSecurityConfig {

    /**
     * The WebClient returned here can be injected where ever we have the need for creating
     * and sending REST requests to other applications
     * @return
     */
    @Bean
    WebClient webClient() {
        ServletBearerExchangeFilterFunction bearer = new ServletBearerExchangeFilterFunction();
        return WebClient.builder()
                .filter(bearer).build();
    }
}
