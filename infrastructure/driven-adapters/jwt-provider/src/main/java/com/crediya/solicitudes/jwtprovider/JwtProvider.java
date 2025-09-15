package com.crediya.solicitudes.jwtprovider;

import com.crediya.solicitudes.dto.JwtClaims;
import com.crediya.solicitudes.exceptions.InvalidTokenException;
import com.crediya.solicitudes.ports.JwtProviderPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();
            String email = claims.get("email", String.class);
            String documentoIdentidad = claims.get("documentoIdentidad", String.class);

            // Obtener la lista de roles de forma segura y evitar el error de casting
            Object rolesObject = claims.get("roles");
            List<String> roles;

            if (rolesObject instanceof List) {
                // Utilizamos el casting seguro para manejar la lista
                roles = ((List<?>) rolesObject).stream()
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .collect(Collectors.toList());
            } else {
                // Si no es una lista, devolvemos una lista vacía para evitar errores
                roles = Collections.emptyList();
            }

            return Mono.just(new JwtClaims(subject, email, documentoIdentidad, roles, token));
        } catch (Exception e) {
            return Mono.error(new InvalidTokenException("Token inválido o expirado"));
        }
    }

    private SecretKey getSigningKey() {
        String secretKey = "esta-es-mi-clave-mucho-mas-extensa-porque-el-jwt-me-rebota-cuando-es-muy-corta-wtf";
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
