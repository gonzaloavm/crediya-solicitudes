package com.crediya.solicitudes.model.tipoprestamo.gateways;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface TipoPrestamoRepository {
    Mono<Boolean> existePorId(BigInteger id);
}