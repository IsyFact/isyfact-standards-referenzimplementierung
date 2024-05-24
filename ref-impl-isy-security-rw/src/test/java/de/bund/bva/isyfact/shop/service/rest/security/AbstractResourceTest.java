package de.bund.bva.isyfact.shop.service.rest.security;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractResourceTest {

    // we need two alternative token factory urls, in order to avoid problems
    // that can arise from caching
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    protected String issuerUriA;

	@Value("${spring.security.oauth2.client.registration.reg-client-a.client-id}")
    protected String clientAId;
    
	@Value("${spring.security.oauth2.client.registration.reg-client-a.client-secret}")
    protected String clientASecret;

    @Value("${isy.security.test.confidential-client.client-id}")
    protected String confidentialClientId;

    @Value("${isy.security.test.confidential-client.client-secret}")
    protected String confidentialClientSecret;

    @BeforeEach
    public void clearAuthentication() {
        // SecurityContext: clear authentication (incl. access token)
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected static Authentication getAuthentication() {
        // SecurityContext: retrieve authentication (incl. access token)
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
