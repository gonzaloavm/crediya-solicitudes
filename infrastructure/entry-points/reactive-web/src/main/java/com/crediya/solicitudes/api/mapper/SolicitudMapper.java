package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.solicitante.ActualizarEstadoSolicitanteRequest;
import com.crediya.solicitudes.api.dto.solicitante.CrearSolicitudRequest;
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

    @Mapping(target = "solicitudId", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "estado.codEstado", source = "codEstado")
    public abstract Solicitud toModel(ActualizarEstadoSolicitanteRequest actualizarEstadoSolicitanteRequest);

    protected TipoPrestamo map(String idTipoPrestamo) {
        return TipoPrestamo.builder()
                .publicTipoPrestamoId(uuidProvider.fromString(idTipoPrestamo))
                .build();
    }
}

