package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.solicitante.ActualizarEstadoSolicitanteRequest;
import com.crediya.solicitudes.api.dto.solicitante.CrearSolicitudRequest;
import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.ports.UuidProviderPort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

@Mapper(componentModel = "spring")
public abstract class SolicitudMapper {

    @Autowired
    protected UuidProviderPort uuidProvider;

    @Mapping(target = "solicitudId", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "tipoPrestamo", source="tipoPrestamoId")
    public abstract Solicitud toModel(CrearSolicitudRequest crearSolicitudRequest);

    @Mapping(target = "estado", source = "estadoId")
    public abstract Solicitud toModel(ActualizarEstadoSolicitanteRequest actualizarEstadoSolicitanteRequest);

    protected TipoPrestamo mapIdToTipoPrestamo(String tipoPrestamoId) {
        return TipoPrestamo.builder()
                .publicTipoPrestamoId(uuidProvider.fromString(tipoPrestamoId))
                .build();
    }

    protected Estado mapIdToEstado(String estadoId) {
        return Estado.builder()
                .publicEstadoId(uuidProvider.fromString(estadoId))
                .build();
    }
}

