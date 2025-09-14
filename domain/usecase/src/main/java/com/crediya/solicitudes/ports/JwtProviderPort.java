package com.crediya.solicitudes.ports;

import com.crediya.solicitudes.dto.JwtClaims;
import reactor.core.publisher.Mono;

import java.util.List;

public interface JwtProviderPort {
    Mono<Boolean> validateToken(String token);
    Mono<JwtClaims> getClaimsFromToken(String token);
}
