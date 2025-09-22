package com.crediya.solicitudes.exception;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: reglas de negocio complejas que no encajan en validaciones simples
public class BusinessRuleException extends DomainException{
    public BusinessRuleException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
