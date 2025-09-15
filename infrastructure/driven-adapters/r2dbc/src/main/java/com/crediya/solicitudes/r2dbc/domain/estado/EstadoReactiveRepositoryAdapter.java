package com.crediya.solicitudes.r2dbc.domain.estado;

import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.estado.gateways.EstadoRepositoryPort;
import com.crediya.solicitudes.r2dbc.entity.EstadoData;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class EstadoReactiveRepositoryAdapter extends ReactiveAdapterOperations<Estado, EstadoData, BigInteger, EstadoReactiveRepository> implements EstadoRepositoryPort {

    public EstadoReactiveRepositoryAdapter(
            EstadoReactiveRepository repository,
            ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Estado.class));
    }

    @Override
    public Mono<Estado> buscarPorId(BigInteger id) {
        return this.repository.findById(id).map(this::toEntity);
    }

    @Override
    public Mono<Estado> buscarPorCodigo(String codEstado) {
        return this.repository.findByCodEstado(codEstado).map(this::toEntity);
    }
}
