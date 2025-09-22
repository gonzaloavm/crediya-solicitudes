package com.crediya.solicitudes.jwtprovider;

import com.crediya.solicitudes.dto.JwtClaims;
import com.crediya.solicitudes.error.ErrorCode;
import com.crediya.solicitudes.exceptions.AuthenticationException;
import com.crediya.solicitudes.ports.JwtProviderPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JwtProvider implements JwtProviderPort {

    @Override
    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> {
            try {
                Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        });
    }

    @Override
    public Mono<JwtClaims> getClaimsFromToken(String token) {
        return Mono.fromCallable(() -> {
                    Claims claims = Jwts.parser()
                            .verifyWith(getSigningKey())
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

                    List<String> roles = extractRolesSafely(claims);

                    return new JwtClaims(
                            claims.getSubject(),
                            claims.get("email", String.class),
                            claims.get("documentoIdentidad", String.class),
                            roles,
                            token
                    );
                })
                .subscribeOn(Schedulers.parallel())
                .onErrorMap(e -> new AuthenticationException(
                        ErrorCode.INVALID_CREDENTIALS,
                        "Falló la autenticación. Token inválido o expirado."
                ));
    }

    private static List<String> extractRolesSafely(Claims claims) {
        Object rolesObject = claims.get("roles");
        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        // Si el claim viene como String con CSV
        if (rolesObject instanceof String csv) {
            return Arrays.stream(csv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
        // Fallback: no hay roles o formato inesperado
        return Collections.emptyList();
    }

    private SecretKey getSigningKey() {
        String secretKey = "esta-es-mi-clave-mucho-mas-extensa-porque-el-jwt-me-rebota-cuando-es-muy-corta-wtf";
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
