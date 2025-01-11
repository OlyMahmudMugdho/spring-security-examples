package com.mahmud.keycloakjwtauth.config;

import com.mahmud.keycloakjwtauth.converter.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {


    private final JwtAuthConverter jwtAuthConverter = new JwtAuthConverter();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.disable()
                )
                .authorizeHttpRequests(
                        requests ->
                                requests
                                        .requestMatchers("/api/v1/demo/hello-2").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                );

        http
                .oauth2ResourceServer(
                        server ->
                                server.jwt(jwtConfigurer -> jwtConfigurer
                                        .jwtAuthenticationConverter(jwtAuthConverter)
                                )
                );
//                .jwt()
//                .jwtAuthenticationConverter(jwtAuthConverter);

        http
                .sessionManagement(
                        sesion ->
                                sesion.sessionCreationPolicy(STATELESS)
                );

        return http.build();
    }
}


/*
*   .oauth2ResourceServer(
                        server ->
                                server.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
* */