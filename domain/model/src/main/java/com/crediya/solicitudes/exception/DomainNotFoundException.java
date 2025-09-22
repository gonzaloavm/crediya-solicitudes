package com.crediya.solicitudes.exception;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: cuando una entidad no existe durante la ejecución de lógica de dominio
public class DomainNotFoundException extends DomainException{
    public DomainNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
