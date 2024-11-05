package de.bund.bva.isyfact.shop.service.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTest {

    protected static final String REALM_URL_PREFIX = "http://localhost:8989/realms/ref-impl-isy-security";

    protected static final ObjectMapper mapper = new ObjectMapper();

    protected String initializeTokenForTechnicalUser(String userName, String userPassword) throws JsonProcessingException {

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        // client auth data, as defined in KeyCloak:
        bodyValues.add("client_id", "confidential-client");
        bodyValues.add("client_secret", "RCX1o8KF1Iez3d0XvcKPOJjiDIFnpKay");
        bodyValues.add("username", userName);
        bodyValues.add("password", userPassword);
        bodyValues.add("grant_type", "password");

        WebClient client1 = WebClient.create();

        Mono<String> response1 = client1.post()
                .uri(REALM_URL_PREFIX + "/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class);

        String token = mapper.readTree(response1.block()).get("access_token").asText();
        assertNotNull(token);

        System.out.println("Token:" + token);
        return token;
    }

    protected String initializeTokenForClient(String clientId, String clientSecret) throws JsonProcessingException {

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        // client auth data, as defined in KeyCloak:
        bodyValues.add("client_id", clientId);
        bodyValues.add("client_secret", clientSecret);
        bodyValues.add("grant_type", "client_credentials");

        WebClient client1 = WebClient.create();

        Mono<String> response1 = client1.post()
                .uri(REALM_URL_PREFIX + "/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class);

        String token = mapper.readTree(response1.block()).get("access_token").asText();
        assertNotNull(token);

        return token;
    }
}
