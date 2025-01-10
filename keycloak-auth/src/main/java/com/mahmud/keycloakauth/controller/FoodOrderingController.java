package com.mahmud.keycloakauth.controller;

import com.mahmud.keycloakauth.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class FoodOrderingController {

    private final WebClient webClient;

    public FoodOrderingController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8088").build();
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }


    @GetMapping("/menu")
    public String menu(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getPreferredUsername());
        } else {
            return "redirect:/";  // Redirect to home if not authenticated
        }
        return "menu";
    }

    @GetMapping("/logout")
    public String logout(@AuthenticationPrincipal OidcUser user, HttpServletRequest request) throws ServletException {
        // Perform Spring Security logout
        request.logout();

        if (user != null) {
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

        // Redirect user to application home
        return "redirect:http://localhost:8082";
    }



/*
*   this method directly logs out, but does not redirect to homepage
*   @GetMapping("/logout")
    public String logout(HttpServletRequest request, @AuthenticationPrincipal OidcUser user) throws ServletException {
        request.logout(); // Logs out from Spring Security

        // Get the ID token of the logged-in user
        String idToken = user.getIdToken().getTokenValue();
        String redirectUri = "http://localhost:8082"; // Your application's home page or desired redirect URL

        // Construct the logout URL with the id_token_hint parameter
        String logoutUrl = "http://localhost:8088/realms/food-ordering-realm/protocol/openid-connect/logout" +
                "?id_token_hint=" + idToken +
                "&redirect_uri=" + redirectUri;

        return "redirect:" + logoutUrl;
    }
*
* */

/*
    it takes to a confirmation page, but after the logout confirmation, it does not redirect to homepage
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout(); // Logs out from Spring Security
        String redirectUri = "http://localhost:8082"; // Your application's home page or desired redirect URL
        return "redirect:http://localhost:8088/realms/food-ordering-realm/protocol/openid-connect/logout?redirect_uri=" + redirectUri;
        //return "redirect:" + keycloakAuthServerUrl + "/realms/" + realm + "/protocol/openid-connect/logout?redirect_uri=" + URLEncoder.encode("http://localhost:8080", StandardCharsets.UTF_8);
        //return "redirect:http://localhost:8088/realms/food-ordering-realm/protocol/openid-connect/logout/";
    }
*/

}