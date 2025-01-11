package com.mahmud.keycloakjwtauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('client-admin')")
    public String privateEndpoint() {
        return "This is a private endpoint accessible with a valid JWT.";
    }
}

