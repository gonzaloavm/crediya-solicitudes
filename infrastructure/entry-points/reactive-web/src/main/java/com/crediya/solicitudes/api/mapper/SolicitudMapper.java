package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.solicitante.SolicitudRequest;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigInteger;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "tipoPrestamo", expression = "java(mapTipoPrestamo(solicitudRequest.idTipoPrestamo()))")
    Solicitud toModel(SolicitudRequest solicitudRequest);

    default TipoPrestamo mapTipoPrestamo(Long idTipoPrestamo) {
        return TipoPrestamo.builder()
                .idTipoPrestamo(BigInteger.valueOf(idTipoPrestamo))
                .build();
    }
}

