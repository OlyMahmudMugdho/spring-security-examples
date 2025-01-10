package com.mahmud.keycloakauth.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final WebClient webClient;

    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void logout(@AuthenticationPrincipal OidcUser user) {
        // Extract ID token
        String idToken = user.getIdToken().getTokenValue();

        // Hardcoded Keycloak logout URL and redirect URI
        String keycloakLogoutUrl = "/realms/food-ordering-realm/protocol/openid-connect/logout";
        String redirectUri = "http://localhost:8082";

        // Call Keycloak logout endpoint in the background
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(keycloakLogoutUrl)
                        .queryParam("id_token_hint", idToken)
                        .queryParam("post_logout_redirect_uri", redirectUri)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> {
                    // Handle any errors during logout request
                    System.err.println("Error during logout: " + e.getMessage());
                    return Mono.empty();
                })
                .subscribe(); // Trigger request asynchronously
    }
}
