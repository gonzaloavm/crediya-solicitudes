package com.crediya.solicitudes.api.dto.solicitante;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitudRequest (

    @NotBlank(message = "El documento de identidad no puede estar vacío")
    String documentoIdentidad,

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    Double monto,

    @NotNull(message = "El plazo no puede ser nulo")
    @Min(value = 1, message = "El plazo debe ser mayor a 0")
    Integer plazo,

    @NotNull(message = "El ID del tipo de préstamo no puede ser nulo")
    Long idTipoPrestamo
){}