package com.crediya.solicitudes.exceptions;

import com.crediya.solicitudes.error.ErrorCode;

// Propósito: la acción de manejo de autenticacion
public class AuthenticationException extends UseCaseException{
    public AuthenticationException(ErrorCode errorCode, String message) { super(errorCode, message); }
}
