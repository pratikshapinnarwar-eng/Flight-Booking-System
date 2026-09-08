package com.flight.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Lets the frontend, running on a different origin, call this API.
 *
 * A browser treats localhost:4200 and localhost:8080 as different origins and
 * blocks the request unless the server explicitly allows it. Postman ignores
 * CORS entirely, which is why an API can work there and still fail in a browser.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOriginPatterns(
                                "http://localhost:4200",
                                "http://localhost:3000",
                                "https://*.ngrok-free.app",
                                "https://*.ngrok.io")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization", "ngrok-skip-browser-warning")
                        .allowCredentials(true);
            }
        };
    }
}
