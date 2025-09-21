package com.crediya.solicitudes.model.solicitud.gateways;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface SolicitudRepositoryPort {
    Mono<Void> guardar(Solicitud solicitud);
    Flux<Solicitud> buscarTodos();
    Flux<Solicitud> buscarPorIdEstadosPaginado(List<BigInteger> idEstados, int page, int size);
    Mono<Void> actualizarEstado(Solicitud solicitud);
}
