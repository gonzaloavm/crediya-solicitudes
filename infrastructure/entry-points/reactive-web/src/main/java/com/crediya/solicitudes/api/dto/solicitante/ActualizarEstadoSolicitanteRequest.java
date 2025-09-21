package com.crediya.solicitudes.api.dto.solicitante;

import jakarta.validation.constraints.NotBlank;

public record ActualizarEstadoSolicitanteRequest(
        @NotBlank(message = "El codigo del estado no puede ser nulo")
        String codEstado
) {}
