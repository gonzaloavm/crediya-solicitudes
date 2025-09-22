package com.crediya.solicitudes.exceptions;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: envoltorio genérico para errores no previstos en la capa de aplicación
public class ApplicationException extends UseCaseException{
    public ApplicationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
