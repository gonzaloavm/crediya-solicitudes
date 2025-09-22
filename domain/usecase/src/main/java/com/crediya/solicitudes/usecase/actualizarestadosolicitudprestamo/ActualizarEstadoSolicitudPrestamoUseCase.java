package com.crediya.solicitudes.usecase.actualizarestadosolicitudprestamo;

import com.crediya.solicitudes.model.estado.gateways.EstadoRepositoryPort;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepositoryPort;
import com.crediya.solicitudes.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ActualizarEstadoSolicitudPrestamoUseCase {

    private static final Logger log = Logger.getLogger(ActualizarEstadoSolicitudPrestamoUseCase.class.getName());

    private final SolicitudRepositoryPort solicitudRepository;
    private final EstadoRepositoryPort estadoRepositoryPort;

    public Mono<Void> actualizarEstado(Solicitud solicitud) {
        return solicitudRepository.buscarPorPublicId(solicitud.getPublicSolicitudId())
                .switchIfEmpty(Mono.error(new IllegalStateException(
                        "No se encontró la solicitud con public ID: " + Arrays.toString(solicitud.getPublicSolicitudId())
                )))
                .flatMap(solicitudExistente ->
                        estadoRepositoryPort.buscarPorPublicId(solicitud.getEstado().getPublicEstadoId())
                                .flatMap(estado -> {
                                    solicitudExistente.setEstado(estado);
                                    return solicitudRepository.actualizarEstado(solicitudExistente);
                                })
                )
                .then();
    }

}
