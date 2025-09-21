package com.crediya.solicitudes.transactional;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.usecase.actualizarestadosolicitudprestamo.ActualizarEstadoSolicitudPrestamoUseCase;
import com.crediya.solicitudes.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionalActualizarEstadoSolicitudPrestamo {

    private final ActualizarEstadoSolicitudPrestamoUseCase actualizarEstadoSolicitudPrestamoUseCase;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> actualizarEstado(Solicitud solicitud){
        Mono<Void> registroChain = actualizarEstadoSolicitudPrestamoUseCase.actualizarEstado(solicitud);
        return transactionalOperator.transactional(registroChain);
    }
}
