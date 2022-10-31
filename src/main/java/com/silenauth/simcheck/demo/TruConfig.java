package com.silenauth.simcheck.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@Configuration
public class TruConfig {
    @Value("${tru.url:}")
    private String url;

    @Value("${tru.client:}")
    private String clientId;

    @Value("${tru.secret:}")
    private String clientSecret;

    @Bean
    public OAuth2RestTemplate truClient() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setAccessTokenUri(url + "/oauth2/v1/token");
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setClientAuthenticationScheme(AuthenticationScheme.header);
        details.setGrantType("client_credentials");
        details.setScope(Arrays.asList(
                "sim_check"
        )); 
        OAuth2RestTemplate tpl = new OAuth2RestTemplate(details);
        tpl.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return tpl;
    }
}
