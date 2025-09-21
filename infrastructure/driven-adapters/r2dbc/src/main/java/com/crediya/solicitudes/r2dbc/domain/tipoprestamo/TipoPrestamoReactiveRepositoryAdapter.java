package com.crediya.solicitudes.r2dbc.domain.tipoprestamo;

import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepositoryPort;
import com.crediya.solicitudes.r2dbc.entity.TipoPrestamoData;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class TipoPrestamoReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        TipoPrestamo,
        TipoPrestamoData,
        BigInteger,
        TipoPrestamoReactiveRepository
        > implements TipoPrestamoRepositoryPort {

    public TipoPrestamoReactiveRepositoryAdapter(TipoPrestamoReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TipoPrestamo.class));
    }

    @Override
    public Mono<Boolean> existePorId(BigInteger id) {
        return repository.existsById(id);
    }

    @Override
    public Mono<Boolean> existePorPublicId(byte[] publicId) {
        return repository.existsByPublicTipoPrestamoId(publicId);
    }

    @Override
    public Mono<TipoPrestamo> buscarPorId(BigInteger id) {
        return repository.findById(id)
                .map(this::toEntity);
    }

    @Override
    public Mono<TipoPrestamo> buscarPorPublicTipoPrestamoId(byte[] id) {
        return repository.findByPublicTipoPrestamoId(id)
                .map(this::toEntity);
    }
}
