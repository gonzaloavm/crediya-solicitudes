package com.crediya.solicitudes.r2dbc.domain.tipoprestamo;

import com.crediya.solicitudes.r2dbc.entity.TipoPrestamoData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface TipoPrestamoReactiveRepository extends ReactiveCrudRepository<TipoPrestamoData, BigInteger>, ReactiveQueryByExampleExecutor<TipoPrestamoData> {
    Mono<TipoPrestamoData> findByPublicTipoPrestamoId(byte[] publicId);
    Mono<Boolean> existsByPublicTipoPrestamoId(byte[] publicId);
}
