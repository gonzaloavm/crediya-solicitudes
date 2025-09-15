package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.api.ApiResult;
import com.crediya.solicitudes.api.dto.solicitante.SolicitudRequest;
import com.crediya.solicitudes.api.mapper.SolicitudMapper;
import com.crediya.solicitudes.dto.JwtClaims;
import com.crediya.solicitudes.dto.SolicitudCompleta;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.transactional.TransactionalEnviarSolicitudPrestamo;
import com.crediya.solicitudes.usecase.listarsolicitudpararevision.ListarSolicitudParaRevisionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes")
@RequiredArgsConstructor
public class SolicitudesController {

    private static final Logger log = LoggerFactory.getLogger(SolicitudesController.class);

    private final TransactionalEnviarSolicitudPrestamo transactionalEnviarSolicitudPrestamo;
    private final ListarSolicitudParaRevisionUseCase listarSolicitudParaRevisionUseCase;
    private final SolicitudMapper solicitudMapper;

    @PostMapping
    @Operation(
            summary = "Registrar una nueva solicitud",
            description = "Registra una solicitud de préstamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud de préstamo realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. campos obligatorios vacíos)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public Mono<ResponseEntity<ApiResult<Void>>> enviarSolicitud(
            @RequestBody SolicitudRequest solicitudRequest,
            @AuthenticationPrincipal JwtClaims userClaims) {
        log.info("Iniciando envío de solicitud. documentoIdentidad={}", solicitudRequest.documentoIdentidad());

        Solicitud solicitud = solicitudMapper.toModel(solicitudRequest);
        String documentoIdentidad = userClaims.documentoIdentidad();
        String usuarioExternalId = userClaims.sub();

        return transactionalEnviarSolicitudPrestamo.enviar(solicitud, documentoIdentidad, usuarioExternalId)
                .doOnSuccess(v -> log.info("Solicitud Enviada Exitosamente {}", ""))
                .thenReturn(ResponseEntity.status(201).body(
                        ApiResult.<Void>builder()
                                .success(true)
                                .code(HttpStatus.CREATED.value())
                                .message("Solicitud enviada con éxito")
                                .build()
                ));
    }

    @GetMapping
    @Operation(
            summary = "Lista de solicitudes",
            description = "Obtiene la lista de solicitudes pendientes de revisión."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasAnyRole('ASESOR')")
    public Mono<ResponseEntity<ApiResult<List<SolicitudCompleta>>>> listarSolicitudes(
            @AuthenticationPrincipal JwtClaims userClaims,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return listarSolicitudParaRevisionUseCase.listar(userClaims.token(), page, size)
                .collectList()
                .map(solicitudes -> ResponseEntity.ok(
                        ApiResult.<List<SolicitudCompleta>>builder()
                                .success(true)
                                .code(HttpStatus.OK.value())
                                .message("Solicitudes obtenidas correctamente")
                                .data(solicitudes)
                                .build()
                ));
    }


}
