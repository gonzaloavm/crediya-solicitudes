package com.crediya.solicitudes.microservicesclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${microservices.autenticacion.url}")
    private String autenticacionUrl;

    @Bean
    public WebClient autenticacionWebClient() {
        return WebClient.builder()
                .baseUrl(autenticacionUrl)
                .build();
    }
}
