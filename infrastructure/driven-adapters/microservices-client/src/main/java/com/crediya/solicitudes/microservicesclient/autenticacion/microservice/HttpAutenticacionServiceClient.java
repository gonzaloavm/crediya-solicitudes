package com.crediya.solicitudes.microservicesclient.autenticacion.microservice;

import com.crediya.solicitudes.dto.UsuarioInfo;
import com.crediya.solicitudes.error.ErrorCode;
import com.crediya.solicitudes.exceptions.ExternalServiceException;
import com.crediya.solicitudes.ports.AutenticacionServiceClientPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class HttpAutenticacionServiceClient implements AutenticacionServiceClientPort {

    private final WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(HttpAutenticacionServiceClient.class);

    public HttpAutenticacionServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${microservices.autenticacion.url}") String autenticacionUrl) {
        this.webClient = webClientBuilder.baseUrl(autenticacionUrl).build();
    }

    @Override
    public Mono<List<UsuarioInfo>> getUsuariosInfo(List<String> externalIds, String token) {

        externalIds.forEach(log::info);

        return webClient.post()
                .uri("/api/v1/usuarios/batch")
                .header("Authorization", "Bearer " + token)
                .bodyValue(externalIds)
                .retrieve()
                .bodyToFlux(UsuarioInfo.class)
                .doOnError(e -> log.error("Error al deserializar usuarios", e))
                .collectList()
                .onErrorResume(e -> {
                    System.err.println("Error al obtener información de usuarios: " + e.getMessage());
                    return Mono.error(new ExternalServiceException(ErrorCode.EXTERNAL_SERVICE_ERROR, "Error de comunicación con el servicio de usuarios."));
                });
    }
}
