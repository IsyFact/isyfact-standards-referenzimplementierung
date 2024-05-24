package de.bund.bva.isyfact.shop.service.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

/**
 * This Configuration class defines REST paths and HTTP methods that are accessible in the current application
 * - authorization is required for HTTP methods all REST paths
 * - except the GET Methods for the produkte resource.
 *
 */
@Configuration
@EnableWebSecurity
public class OAuth2ServerSecurityConfig {

    /**
     *  The corresponding filtering is applied by the Spring REST framework before the Controller methods are reached.
     *  See: https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-securityfilterchain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.GET, "/shop/api/v1/produkte/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

}