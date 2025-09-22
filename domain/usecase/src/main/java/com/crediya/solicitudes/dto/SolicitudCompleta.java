package com.crediya.solicitudes.dto;


public record SolicitudCompleta (
        String solicitudId,
        double monto,
        int plazo,
        String email,
        String nombre,
        String tipoPrestamo,
        double tasaInteres,
        String estadoSolicitud,
        double salarioBase,
        double montoMensualSolicitud
){}
