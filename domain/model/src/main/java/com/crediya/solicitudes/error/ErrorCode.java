package com.crediya.solicitudes.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    REQUIRED_FIELD("1001", "REQUIRED_FIELD", "El campo es obligatorio y no fue proporcionado."),
    INVALID_FORMAT("1002", "INVALID_FORMAT", "El formato del campo no es válido."),
    VALUE_OUT_OF_RANGE("1003", "VALUE_OUT_OF_RANGE", "El valor está fuera del rango permitido."),

    INVALID_CREDENTIALS("2001", "INVALID_CREDENTIALS", "Las credenciales de autenticación son incorrectas."),
    EXPIRED_TOKEN("2002", "EXPIRED_TOKEN", "El token de sesión ha expirado."),

    RESOURCE_NOT_FOUND("3001", "RESOURCE_NOT_FOUND", "El recurso solicitado no fue encontrado."),
    RESOURCE_ALREADY_EXISTS("3002", "RESOURCE_ALREADY_EXISTS", "Ya existe un recurso con los mismos identificadores."),

    UNAUTHORIZED_ACCESS("4001", "UNAUTHORIZED_ACCESS", "El usuario no tiene permiso para realizar esta acción o acceder a este recurso."),

    SERVICE_ERROR("5001", "SERVICE_ERROR", "Ha ocurrido un error inesperado."),
    EXTERNAL_SERVICE_ERROR("5002", "EXTERNAL_SERVICE_ERROR", "Ocurrió un error al comunicarse con un servicio externo."),
    PROCESSING_ERROR("5003", "PROCESSING_ERROR", "Ocurrió un error al procesar la solicitud.");

    private final String code;
    private final String title;
    private final String defaultMesage;
}
