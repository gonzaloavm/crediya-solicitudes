package com.crediya.solicitudes.exception;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: violaciones de reglas de negocio genéricas (campo obligatorio, formato, rangos)
public class DomainValidationException extends DomainException{
    public DomainValidationException(ErrorCode errorCode, String message) { super(errorCode, message); }
}
