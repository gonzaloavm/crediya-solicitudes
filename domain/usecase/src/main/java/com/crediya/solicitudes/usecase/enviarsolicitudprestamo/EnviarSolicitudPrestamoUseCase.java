package com.crediya.solicitudes.usecase.enviarsolicitudprestamo;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EnviarSolicitudPrestamoUseCase {

    private final SolicitudRepository solicitudRepository;

    public Mono<Void> enviar(Solicitud solicitud){
        return solicitudRepository.enviar(solicitud);
    }
}
