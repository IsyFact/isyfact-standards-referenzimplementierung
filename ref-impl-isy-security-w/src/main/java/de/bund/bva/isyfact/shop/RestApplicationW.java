package de.bund.bva.isyfact.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * The Main class of this project:
 * A REST-Server offering
 * - a secured PUT-Endpoint for updating a Produkt
 *   This is done by calling the Update-Produkt endpoint of RestApplicationRW
 */
@SpringBootApplication
@Configuration
public class RestApplicationW {

    public static void main(String[] args) {

        SpringApplication.run(RestApplicationW.class, args);
    }

}