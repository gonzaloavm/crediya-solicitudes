package com.crediya.solicitudes.transactional;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionalEnviarSolicitudPrestamo {

    private final EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> enviar(Solicitud solicitud){
        Mono<Void> registroChain = enviarSolicitudPrestamoUseCase.enviar(solicitud);
        return transactionalOperator.transactional(registroChain);
    }
}
