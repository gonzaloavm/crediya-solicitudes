package com.crediya.solicitudes.api.exceptionhandler;

import com.crediya.solicitudes.api.SolicitudesController;
import com.crediya.solicitudes.api.dto.api.ApiResult;
import com.crediya.solicitudes.model.tipoprestamo.exception.TipoPrestamoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice(assignableTypes = SolicitudesController.class)
public class SolicitudExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SolicitudExceptionHandler.class);

    @ExceptionHandler(TipoPrestamoNoEncontradoException.class)
    public Mono<ResponseEntity<ApiResult<Void>>> handleCampoObligatorio(TipoPrestamoNoEncontradoException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResult.<Void>builder()
                        .success(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        ));
    }

    // Handler para excepciones genéricas no manejadas
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiResult<Void>>> handleGenericException(Exception ex) {
        log.error("Ha ocurrido un error inesperado: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResult.<Void>builder()
                        .success(false)
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Ha ocurrido un error inesperado. Por favor, inténtelo de nuevo más tarde.")
                        .build()
        ));
    }
}
