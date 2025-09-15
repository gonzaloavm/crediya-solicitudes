package com.crediya.solicitudes.dto;


public record SolicitudCompleta (
    double monto,
    int plazo,
    String email,
    String nombre,
    String tipo_prestamo,
    double tasa_interes,
    String estado_solicitud,
    double salario_base,
    double monto_mensual_solicitud
){}
