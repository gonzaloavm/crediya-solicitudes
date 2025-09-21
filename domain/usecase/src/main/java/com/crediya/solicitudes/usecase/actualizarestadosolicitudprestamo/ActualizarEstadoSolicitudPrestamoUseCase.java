package com.crediya.solicitudes.usecase.actualizarestadosolicitudprestamo;

import com.crediya.solicitudes.model.estado.gateways.EstadoRepositoryPort;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ActualizarEstadoSolicitudPrestamoUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final EstadoRepositoryPort estadoRepositoryPort;

    public Mono<Void> actualizarEstado(Solicitud solicitud) {
        return estadoRepositoryPort.buscarPorCodigo(solicitud.getEstado().getCodEstado())
                .flatMap(estado -> {
                    solicitud.setEstado(estado);
                    return solicitudRepository.actualizarEstado(solicitud);
                })
                .then();
    }
}
