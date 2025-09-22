package com.crediya.solicitudes.usecase.enviarsolicitudprestamo;

import com.crediya.solicitudes.error.ErrorCode;
import com.crediya.solicitudes.exception.BusinessRuleException;
import com.crediya.solicitudes.exception.DomainNotFoundException;
import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepositoryPort;
import com.crediya.solicitudes.ports.UuidProviderPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class EnviarSolicitudPrestamoUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final TipoPrestamoRepositoryPort tipoPrestamoRepository;
    private final UuidProviderPort uuidProviderPort;
    private static final Logger logger = Logger.getLogger(EnviarSolicitudPrestamoUseCase.class.getName());

    public Mono<Void> enviar(Solicitud solicitud, String documentoIdentidadJWT, String usuarioExternalIdJWT) {
        return validarIntegridadUsuario(solicitud, documentoIdentidadJWT, usuarioExternalIdJWT)
                .flatMap(this::validarTipoPrestamoExistente)
                .flatMap(this::prepararSolicitud)
                .flatMap(solicitudRepository::guardar)
                .doOnSuccess(v -> logger.info("Solicitud registrada exitosamente para usuarioExternalId=" + usuarioExternalIdJWT));
    }

    //region CONFIGURACION DE SOLICITUD

    private Mono<Solicitud> prepararSolicitud(Solicitud solicitud) {

        // El objeto Estado representa el concepto de negocio
        Estado estadoPendiente = Estado.builder()
                .estadoId(BigInteger.valueOf(1))
                .build();

        return tipoPrestamoRepository.buscarPorPublicTipoPrestamoId(solicitud.getTipoPrestamo().getPublicTipoPrestamoId())
                .map(tipoPrestamo ->
                        solicitud.toBuilder()
                                .estado(estadoPendiente) // Estado "Pendiente de revisión"
                                .publicSolicitudId(uuidProviderPort.toBytes(uuidProviderPort.generate()))
                                .tipoPrestamo(tipoPrestamo)
                                .build()
                );
    }

    //endregion

    //region VALIDACIONES

    private Mono<Solicitud> validarIntegridadUsuario(Solicitud solicitud, String documentoIdentidadJWT, String usuarioExternalIdJWT) {

        boolean coincideDocumento = documentoIdentidadJWT.equals(solicitud.getDocumentoIdentidad());
        boolean coincideExternalId = usuarioExternalIdJWT.equals(solicitud.getUsuarioExternalId());

        if (!coincideDocumento || !coincideExternalId) {
            logger.fine("Validación de integridad fallida: documentoIdentidad=" +
                    solicitud.getDocumentoIdentidad() + ", usuarioExternalId=" + solicitud.getUsuarioExternalId());
            return Mono.error(new BusinessRuleException(ErrorCode.UNAUTHORIZED_ACCESS, "Los datos del JWT no coinciden con la solicitud enviada."));
        }

        return Mono.just(solicitud);
    }

    private Mono<Solicitud> validarTipoPrestamoExistente(Solicitud solicitud) {
        return tipoPrestamoRepository.existePorPublicId(solicitud.getTipoPrestamo().getPublicTipoPrestamoId())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.just(solicitud);
                    } else {
                        logger.warning("Tipo de préstamo no encontrado: id=" + solicitud.getTipoPrestamo().getTipoPrestamoId());
                        return Mono.error(new DomainNotFoundException(ErrorCode.RESOURCE_NOT_FOUND, "El tipo de préstamo no existe."));
                    }
                });
    }

    //endregion

}
