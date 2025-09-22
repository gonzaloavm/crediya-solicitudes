package com.crediya.solicitudes.exception;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: conflicto al momento de intentar procesar
public class ConflictException extends DomainException{
    protected ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
