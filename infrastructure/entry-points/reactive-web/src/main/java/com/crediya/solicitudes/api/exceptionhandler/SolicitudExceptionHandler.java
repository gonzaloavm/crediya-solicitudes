package com.crediya.solicitudes.api.exceptionhandler;

import com.crediya.solicitudes.api.SolicitudesController;
import com.crediya.solicitudes.api.dto.api.ApiResult;
import com.crediya.solicitudes.model.solicitud.exception.IdentidadNoCoincideException;
import com.crediya.solicitudes.model.tipoprestamo.exception.TipoPrestamoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice(assignableTypes = SolicitudesController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SolicitudExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SolicitudExceptionHandler.class);

    @ExceptionHandler(TipoPrestamoNoEncontradoException.class)
    public Mono<ResponseEntity<ApiResult<Void>>> handleTipoPrestamoNoEncontrado(TipoPrestamoNoEncontradoException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResult.<Void>builder()
                        .success(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        ));
    }

    @ExceptionHandler(IdentidadNoCoincideException.class)
    public Mono<ResponseEntity<ApiResult<Void>>> handleIdentidadNoCoincide(IdentidadNoCoincideException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResult.<Void>builder()
                        .success(false)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
        ));
    }

}
