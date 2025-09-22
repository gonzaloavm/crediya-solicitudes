package com.crediya.solicitudes.exceptions;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: la acción choca con el estado actual (recurso duplicado, versión incorrecta).
public class ConflictException extends UseCaseException{
    public ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
