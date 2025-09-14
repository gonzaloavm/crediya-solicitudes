package com.crediya.solicitudes.usecase.enviarsolicitudprestamo;

import com.crediya.solicitudes.model.solicitud.exception.IdentidadNoCoincideException;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import com.crediya.solicitudes.model.tipoprestamo.exception.TipoPrestamoNoEncontradoException;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class EnviarSolicitudPrestamoUseCase {

    private final SolicitudRepository solicitudRepository;
    private final TipoPrestamoRepository tipoPrestamoRepository;
    private static final Logger logger = Logger.getLogger(EnviarSolicitudPrestamoUseCase.class.getName());

    public Mono<Void> enviar(Solicitud solicitud, String documentoIdentidadJWT, String usuarioExternalIdJWT) {
        return validarIntegridadUsuario(solicitud, documentoIdentidadJWT, usuarioExternalIdJWT)
                .flatMap(this::validarTipoPrestamoExistente)
                .flatMap(this::asignarEstadoInicial)
                .flatMap(solicitudRepository::guardar)
                .doOnSuccess(v -> logger.info("Solicitud registrada exitosamente para usuarioExternalId=" + usuarioExternalIdJWT));
    }

    //region CONFIGURACION DE SOLICITUD

    private Mono<Solicitud> asignarEstadoInicial(Solicitud solicitud) {
        return Mono.just(solicitud)
                .map(s -> {
                    s.setIdEstado(BigInteger.valueOf(1)); // Estado "Pendiente de revisión"
                    return s;
                });
    }

    //endregion

    //region VALIDACIONES

    private Mono<Solicitud> validarIntegridadUsuario(Solicitud solicitud, String documentoIdentidadJWT, String usuarioExternalIdJWT) {

        boolean coincideDocumento = documentoIdentidadJWT.equals(solicitud.getDocumentoIdentidad());
        boolean coincideExternalId = usuarioExternalIdJWT.equals(solicitud.getUsuarioExternalId());

        if (!coincideDocumento || !coincideExternalId) {
            logger.fine("Validación de integridad fallida: documentoIdentidad=" +
                    solicitud.getDocumentoIdentidad() + ", usuarioExternalId=" + solicitud.getUsuarioExternalId());
            return Mono.error(new IdentidadNoCoincideException("Los datos del JWT no coinciden con la solicitud enviada."));
        }

        return Mono.just(solicitud);
    }

    private Mono<Solicitud> validarTipoPrestamoExistente(Solicitud solicitud) {
        return tipoPrestamoRepository.existePorId(solicitud.getIdTipoPrestamo())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.just(solicitud);
                    } else {
                        logger.warning("Tipo de préstamo no encontrado: id=" + solicitud.getIdTipoPrestamo());
                        return Mono.error(new TipoPrestamoNoEncontradoException("El tipo de préstamo no existe."));
                    }
                });
    }

    //endregion

}
