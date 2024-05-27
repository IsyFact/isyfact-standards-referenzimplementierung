package de.bund.bva.isyfact.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * The Main class of this project
 */
@SpringBootApplication
@Configuration
public class RestApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApplication.class, args);
    }
}