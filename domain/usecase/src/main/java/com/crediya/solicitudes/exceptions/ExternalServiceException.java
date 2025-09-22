package com.crediya.solicitudes.exceptions;

import com.crediya.solicitudes.error.ErrorCode;

public class ExternalServiceException extends UseCaseException{
    public ExternalServiceException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
