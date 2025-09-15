package com.crediya.solicitudes.dto;

public record UsuarioInfo(
    String usuarioExternalId,
    String nombre,
    String apellido,
    String email,
    Double salarioBase
){}
