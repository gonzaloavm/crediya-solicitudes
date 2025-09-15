package com.crediya.solicitudes.model.tipoprestamo.gateways;

import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface TipoPrestamoRepositoryPort {
    Mono<Boolean> existePorId(BigInteger id);
    Mono<TipoPrestamo> buscarPorId(BigInteger id);
}