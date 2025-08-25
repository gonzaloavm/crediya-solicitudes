package com.crediya.solicitudes.api.mapper;

import com.crediya.solicitudes.api.dto.solicitante.SolicitudRequest;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    @Mapping(target = "idRol", ignore = true)
    Solicitud toModel(SolicitudRequest usuarioRequest);
}

