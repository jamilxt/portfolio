package com.pondit.portfolio.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    /**
     * Configures the OpenAPI specification for the application.
     *
     * This method sets up the security requirements and components for the OpenAPI
     * documentation, including a bearer authentication scheme for securing endpoints.
     *
     * @return an instance of {@link OpenAPI} with the configured security settings.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    /**
     * Creates a SecurityScheme for bearer authentication.
     *
     * This method defines a security scheme of type HTTP with a bearer format
     * set to JWT, which is used for securing API endpoints.
     *
     * @return a {@link SecurityScheme} configured for bearer authentication.
     */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}