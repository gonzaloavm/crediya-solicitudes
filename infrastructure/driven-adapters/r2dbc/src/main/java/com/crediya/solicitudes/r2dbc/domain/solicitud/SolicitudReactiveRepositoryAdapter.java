package com.crediya.solicitudes.r2dbc.domain.solicitud;

import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SolicitudReactiveRepositoryAdapter.class);

    public SolicitudReactiveRepositoryAdapter(
            SolicitudReactiveRepository repository,
            ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Solicitud.class));
    }

    @Override
    public Mono<Void> guardar(Solicitud usuario) {
        SolicitudData data = toData(usuario);
        data.setEstadoId(usuario.getEstado().getEstadoId());
        data.setTipoPrestamoId(usuario.getTipoPrestamo().getTipoPrestamoId());
        return repository.save(data).then();
    }

    @Override
    public Mono<Solicitud> buscarPorPublicId(byte[] publicId) {
        return repository.findByPublicSolicitudId(publicId).
                map(this::toEntity);
    }

    @Override
    public Flux<Solicitud> buscarPorIdEstadosPaginado(List<BigInteger> idEstados, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("solicitudId").descending());

        return repository.findByEstadoIdIn(idEstados, pageable)
                .map(data -> Solicitud.builder()
                        .publicSolicitudId(data.getPublicSolicitudId())
                        .solicitudId(data.getSolicitudId())
                        .monto(data.getMonto())
                        .plazo(data.getPlazo())
                        .documentoIdentidad(data.getDocumentoIdentidad())
                        .estado(Estado.builder()
                                .estadoId(data.getEstadoId())
                                .build())
                        .tipoPrestamo(TipoPrestamo.builder()
                                .tipoPrestamoId(data.getTipoPrestamoId())
                                .build())
                        .usuarioExternalId(data.getUsuarioExternalId())
                        .build());
    }

    @Override
    public Mono<Void> actualizarEstado(Solicitud solicitud) {

        log.info("Estado recuperado: solicitudId={}, estadoId={}", solicitud.getSolicitudId(), solicitud.getEstado().getEstadoId());

        return repository.actualizarIdEstado(
                        solicitud.getSolicitudId(),
                        solicitud.getEstado().getEstadoId()
                )
                .flatMap(rows -> {
                    if (rows == 0) {
                        return Mono.error(new IllegalStateException("No se encontró la solicitud con ID: " + solicitud.getSolicitudId() + " para actualizar estado " + solicitud.getEstado().getEstadoId()));
                    }
                    return Mono.empty();
                });
    }
}
