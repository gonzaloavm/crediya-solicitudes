package com.crediya.solicitudes.model.estado.gateways;

import com.crediya.solicitudes.model.estado.Estado;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface EstadoRepositoryPort {
    Mono<Estado> buscarPorId(BigInteger id);
    Mono<Estado> buscarPorCodigo(String codEstado);
}
