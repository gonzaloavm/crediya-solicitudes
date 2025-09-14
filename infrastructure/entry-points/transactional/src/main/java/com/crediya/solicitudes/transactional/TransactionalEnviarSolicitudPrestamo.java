package com.crediya.solicitudes.transactional;

import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class TransactionalEnviarSolicitudPrestamo {

    private final EnviarSolicitudPrestamoUseCase enviarSolicitudPrestamoUseCase;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> enviar(Solicitud solicitud, String documentoIdentidad, String usuarioExternalId){
        Mono<Void> registroChain = enviarSolicitudPrestamoUseCase.enviar(solicitud, documentoIdentidad, usuarioExternalId);
        return transactionalOperator.transactional(registroChain);
    }
}
