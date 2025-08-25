package com.crediya.solicitudes.usecase.enviarsolicitudprestamo;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepository;
import com.crediya.solicitudes.model.tipoprestamo.exception.TipoPrestamoNoEncontradoException;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RequiredArgsConstructor
public class EnviarSolicitudPrestamoUseCase {

    private final SolicitudRepository solicitudRepository;
    private final TipoPrestamoRepository tipoPrestamoRepository;

    public Mono<Void> enviar(Solicitud solicitud) {
        return validarTipoPrestamoExistente(solicitud)
                .flatMap(this::asignarEstadoInicial)
                .flatMap(solicitudRepository::guardar);
    }

    private Mono<Solicitud> validarTipoPrestamoExistente(Solicitud solicitud) {
        return tipoPrestamoRepository.existePorId(solicitud.getIdTipoPrestamo())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.just(solicitud);
                    } else {
                        return Mono.error(new TipoPrestamoNoEncontradoException("El tipo de préstamo no existe."));
                    }
                });
    }

    private Mono<Solicitud> asignarEstadoInicial(Solicitud solicitud) {
        return Mono.just(solicitud)
                .map(s -> {
                    s.setIdEstado(BigInteger.valueOf(1));
                    return s;
                });
    }
}
