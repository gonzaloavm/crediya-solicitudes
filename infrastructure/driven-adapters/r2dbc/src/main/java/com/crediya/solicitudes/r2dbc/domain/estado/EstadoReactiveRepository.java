package com.crediya.solicitudes.r2dbc.domain.estado;

import com.crediya.solicitudes.r2dbc.entity.EstadoData;
import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface EstadoReactiveRepository extends ReactiveCrudRepository<EstadoData, BigInteger>, ReactiveQueryByExampleExecutor<EstadoData> {
    Mono<EstadoData> findByCodEstado(String codEstado);
}
