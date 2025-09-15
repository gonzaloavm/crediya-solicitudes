package com.crediya.solicitudes.r2dbc.domain.solicitud;

import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@Repository
public class SolicitudReactiveRepositoryAdapter extends ReactiveAdapterOperations<Solicitud, SolicitudData, BigInteger, SolicitudReactiveRepository> implements SolicitudRepositoryPort {

    public SolicitudReactiveRepositoryAdapter(
            SolicitudReactiveRepository repository,
            ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Solicitud.class));
    }

    @Override
    public Mono<Void> guardar(Solicitud usuario) {
        SolicitudData data = toData(usuario);
        data.setIdEstado(usuario.getEstado().getIdEstado());
        data.setIdTipoPrestamo(usuario.getTipoPrestamo().getIdTipoPrestamo());
        return repository.save(data).then();
    }

    @Override
    public Flux<Solicitud> buscarTodos() {
        return super.findAll();
    }

//    @Override
//    public Flux<Solicitud> buscarPorIdEstadosPaginado(List<BigInteger> idEstados, int page, int size) {
//        return repository.findByIdEstadoIn(idEstados)
//                .map(data -> Solicitud.builder()
//                        .idSolicitud(data.getIdSolicitud())
//                        .monto(data.getMonto())
//                        .plazo(data.getPlazo())
//                        .documentoIdentidad(data.getDocumentoIdentidad())
//                        .estado(Estado.builder()
//                                .idEstado(data.getIdEstado())
//                                .build())
//                        .tipoPrestamo(TipoPrestamo.builder()
//                                .idTipoPrestamo(data.getIdTipoPrestamo())
//                                .build())
//                        .usuarioExternalId(data.getUsuarioExternalId())
//                        .build());
//    }

    @Override
    public Flux<Solicitud> buscarPorIdEstadosPaginado(List<BigInteger> idEstados, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("idSolicitud").descending());

        return repository.findByIdEstadoIn(idEstados, pageable)
                .map(data -> Solicitud.builder()
                        .idSolicitud(data.getIdSolicitud())
                        .monto(data.getMonto())
                        .plazo(data.getPlazo())
                        .documentoIdentidad(data.getDocumentoIdentidad())
                        .estado(Estado.builder()
                                .idEstado(data.getIdEstado())
                                .build())
                        .tipoPrestamo(TipoPrestamo.builder()
                                .idTipoPrestamo(data.getIdTipoPrestamo())
                                .build())
                        .usuarioExternalId(data.getUsuarioExternalId())
                        .build());
    }
}
