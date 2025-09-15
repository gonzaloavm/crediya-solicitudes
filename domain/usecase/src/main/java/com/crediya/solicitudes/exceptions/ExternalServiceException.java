package com.crediya.solicitudes.exceptions;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String error) {
        super(error);
    }
}