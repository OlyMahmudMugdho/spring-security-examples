package com.mahmud.keycloakauth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodOrderingController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Maps the "/menu" URL to the menu page and sets the authenticated user's username in the model.
     *
     * @param user  the authenticated OIDC (OpenID Connect) user
     * @param model Model object for passing data to the view
     * @return the name of the view to render for the menu page, or redirects to home if user is not authenticated
     */
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
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout(); // Logs out from Spring Security
        String redirectUri = "http://localhost:8082"; // Your application's home page or desired redirect URL
        return "redirect:http://localhost:8088/realms/food-ordering-realm/protocol/openid-connect/logout?redirect_uri=" + redirectUri;
        //return "redirect:" + keycloakAuthServerUrl + "/realms/" + realm + "/protocol/openid-connect/logout?redirect_uri=" + URLEncoder.encode("http://localhost:8080", StandardCharsets.UTF_8);
        //return "redirect:http://localhost:8088/realms/food-ordering-realm/protocol/openid-connect/logout/";
    }

}
