package com.crediya.solicitudes.ports;

import com.crediya.solicitudes.dto.UsuarioInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AutenticacionServiceClientPort {
    Mono<List<UsuarioInfo>> getUsuariosInfo(List<String> externalIds, String token);
}
