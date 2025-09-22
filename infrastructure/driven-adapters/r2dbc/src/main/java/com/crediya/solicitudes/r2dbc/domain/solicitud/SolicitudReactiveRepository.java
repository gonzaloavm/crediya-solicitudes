package com.crediya.solicitudes.r2dbc.domain.solicitud;

import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudData, BigInteger>, ReactiveQueryByExampleExecutor<SolicitudData> {

    Flux<SolicitudData> findByEstadoIdIn(List<BigInteger> idEstados, Pageable pageable);

    @Query(
        """
            UPDATE solicitudes
            SET estado_id = :estadoId
            WHERE solicitud_id = :solicitudId
        """
    )
    Mono<Integer> actualizarIdEstado(BigInteger solicitudId, BigInteger estadoId);

    Mono<SolicitudData> findByPublicSolicitudId(byte[] publicId);
}
