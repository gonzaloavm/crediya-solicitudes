package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepository;
import com.crediya.solicitudes.r2dbc.entity.SolicitudEntity;
import com.crediya.solicitudes.r2dbc.entity.TipoPrestamoEntity;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class TipoPrestamoReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        TipoPrestamo,
        TipoPrestamoEntity,
        BigInteger,
        TipoPrestamoReactiveRepository
        > implements TipoPrestamoRepository {

    public TipoPrestamoReactiveRepositoryAdapter(TipoPrestamoReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.mapBuilder(d, TipoPrestamo.class));
    }

    @Override
    public Mono<Boolean> existePorId(BigInteger id) {
        return repository.existsById(id);
    }
}
