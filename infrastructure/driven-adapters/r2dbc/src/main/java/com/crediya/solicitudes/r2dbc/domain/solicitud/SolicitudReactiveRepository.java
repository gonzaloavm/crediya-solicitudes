package com.crediya.solicitudes.r2dbc.domain.solicitud;

import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.List;

public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudData, BigInteger>, ReactiveQueryByExampleExecutor<SolicitudData> {
    Flux<SolicitudData> findByIdEstadoIn(List<BigInteger> idEstados, Pageable pageable);
}
