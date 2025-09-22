package com.crediya.solicitudes.exceptions;

import com.crediya.solicitudes.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class UseCaseException extends RuntimeException {

    private final ErrorCode errorCode;

    protected UseCaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected UseCaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
