package com.crediya.solicitudes.dto;

import java.util.List;

public record JwtClaims(
        String sub,
        String email,
        String documentoIdentidad,
        List<String> roles
) {}
