package de.bund.bva.isyfact.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * The Main class of this project:
 * A REST-Server offering
 * - public GET-Endpoints for retrieving Produkt data
 * - secured PUT-Endpoint for updating a Produkt
 */
@SpringBootApplication
@Configuration
public class RestApplicationRW {

    public static void main(String[] args) {
        SpringApplication.run(RestApplicationRW.class, args);
    }

}