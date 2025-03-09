package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private static final String[] WHITELIST = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/login", "/api/register"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .cors().and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/driver").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/driver").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/driver/{driverId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/driver/{driverId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/driver/{driverId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/driverRequest").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/driverRequest").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/driverRequest/{driverRequestId}").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/driverRequest/user/{userId}").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/driverRequest/{driverRequestId}").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/driverRequest/driver/{driverId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/driverRequest/{driverRequestId}").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/feedback").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/feedback/{feedbackId}").hasAnyRole("ADMIN","CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/feedback").hasAnyRole("ADMIN","CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/feedback/user/{userId}").hasAnyRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/feedback/{feedbackId}").hasAnyRole("CUSTOMER")
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))                        
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private SecurityScheme createApiKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement()
                .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createApiKeyScheme()));
    }
}


