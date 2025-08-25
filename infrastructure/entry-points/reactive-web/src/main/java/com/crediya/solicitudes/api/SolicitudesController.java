package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.api.ApiResult;
import com.crediya.solicitudes.api.dto.solicitante.SolicitudRequest;
import com.crediya.solicitudes.api.mapper.SolicitudMapper;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.transactional.TransactionalEnviarSolicitudPrestamo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/solicitudes")
@RequiredArgsConstructor
public class SolicitudesController {

    private static final Logger log = LoggerFactory.getLogger(SolicitudesController.class);

    private final TransactionalEnviarSolicitudPrestamo transactionalEnviarSolicitudPrestamo;
    private final SolicitudMapper solicitudMapper;

    @PostMapping
    @Operation(summary = "Registrar una nueva solicitud", description = "Registra una solicitud de préstamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud de préstamo realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. campos obligatorios vacíos)"),
    })
    public Mono<ResponseEntity<ApiResult<Void>>> enviar(@RequestBody SolicitudRequest solicitudRequest) {
        log.info("Iniciando envío de solicitud del usuario: {}", solicitudRequest.documentoIdentidad());

        Solicitud solicitud = solicitudMapper.toModel(solicitudRequest);

        return transactionalEnviarSolicitudPrestamo.enviar(solicitud)
                .doOnSuccess(v -> log.info("Solicitud Enviada Exitosamente {}", ""))
                .thenReturn(ResponseEntity.status(201).body(
                        ApiResult.<Void>builder()
                                .success(true)
                                .code(201)
                                .message("Solicitud enviada con éxito")
                                .build()
                ));
    }

}
