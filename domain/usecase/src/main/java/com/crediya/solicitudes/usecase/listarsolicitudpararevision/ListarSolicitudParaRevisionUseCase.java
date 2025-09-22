package com.crediya.solicitudes.usecase.listarsolicitudpararevision;

import com.crediya.solicitudes.dto.SolicitudCompleta;
import com.crediya.solicitudes.dto.UsuarioInfo;
import com.crediya.solicitudes.exceptions.ExternalServiceException;
import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.estado.gateways.EstadoRepositoryPort;
import com.crediya.solicitudes.model.solicitud.Solicitud;
import com.crediya.solicitudes.model.solicitud.gateways.SolicitudRepositoryPort;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import com.crediya.solicitudes.model.tipoprestamo.gateways.TipoPrestamoRepositoryPort;
import com.crediya.solicitudes.ports.AutenticacionServiceClientPort;
import com.crediya.solicitudes.ports.UuidProviderPort;
import com.crediya.solicitudes.usecase.enviarsolicitudprestamo.EnviarSolicitudPrestamoUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ListarSolicitudParaRevisionUseCase {

    private final SolicitudRepositoryPort solicitudRepositoryPort;
    private final EstadoRepositoryPort estadoRepositoryPort;
    private final TipoPrestamoRepositoryPort tipoPrestamoRepositoryPort;
    private final AutenticacionServiceClientPort autenticacionServiceClientPort;
    private final UuidProviderPort uuidProvider;

    private static final Logger log = Logger.getLogger(EnviarSolicitudPrestamoUseCase.class.getName());

    public Flux<SolicitudCompleta> listar(String token, int page, int size) {
        return solicitudRepositoryPort.buscarPorIdEstadosPaginado(List.of(BigInteger.valueOf(1)), page, size)
                .flatMap(solicitud -> procesarSolicitudIndividual(solicitud, token))
                .onErrorResume(e -> {
                    log.severe("Error procesando solicitudes: " + e.getMessage());
                    return Mono.error(new ExternalServiceException("Error procesando solicitudes"));
                });
    }

    private Mono<SolicitudCompleta> procesarSolicitudIndividual(Solicitud solicitud, String token) {
        return Mono.zip(
                estadoRepositoryPort.buscarPorId(solicitud.getEstado().getEstadoId())
                        .onErrorResume(e -> {
                            log.warning("Error obteniendo estado: " + e.getMessage());
                            return Mono.just(new Estado()); // Estado por defecto
                        }),
                tipoPrestamoRepositoryPort.buscarPorId(solicitud.getTipoPrestamo().getTipoPrestamoId())
                        .onErrorResume(e -> {
                            log.warning("Error obteniendo tipo préstamo: " + e.getMessage());
                            return Mono.just(new TipoPrestamo()); // Tipo préstamo por defecto
                        })
        ).flatMap(tuple -> {
            Solicitud solicitudEnriquecida = solicitud.toBuilder()
                    .estado(tuple.getT1())
                    .tipoPrestamo(tuple.getT2())
                    .build();

            return autenticacionServiceClientPort.getUsuariosInfo(
                    getUsuarioIdsUnicosNoNulos(solicitudEnriquecida),
                    token
            ).flatMap(usuarios -> {
                if (usuarios.isEmpty()) {
                    log.warning("No se encontró usuario para externalId: " + solicitudEnriquecida.getUsuarioExternalId());
                    return Mono.just(construirSolicitudCompleta(solicitudEnriquecida, null));
                }

                UsuarioInfo usuarioInfo = usuarios.get(0);
                return Mono.just(construirSolicitudCompleta(solicitudEnriquecida, usuarioInfo));
            });
        }).onErrorResume(e -> {
            log.severe("Error procesando solicitud individual: " + e.getMessage());
            return Mono.empty(); // Omitir esta solicitud y continuar con las demás
        });
    }

    private List<String> getUsuarioIdsUnicosNoNulos(Solicitud solicitud) {
        String id = solicitud.getUsuarioExternalId();

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID externo de usuario no puede ser nulo ni vacío");
        }

        return List.of(id.trim()); // trim para evitar espacios accidentales
    }

    private SolicitudCompleta construirSolicitudCompleta(Solicitud solicitud, UsuarioInfo usuarioInfo) {
        // Manejar caso donde usuarioInfo es null
        if (usuarioInfo == null) {
            return new SolicitudCompleta(
                    uuidProvider.toString(solicitud.getPublicSolicitudId()),
                    solicitud.getMonto(),
                    solicitud.getPlazo(),
                    "Email no disponible",
                    "Nombre no disponible",
                    solicitud.getTipoPrestamo().getNombre(),
                    solicitud.getTipoPrestamo().getTasaInteres(),
                    solicitud.getEstado().getNombre(),
                    0.0,
                    0.0
            );
        }

        double monto = solicitud.getMonto();
        int plazo = solicitud.getPlazo();
        double tasaInteresMensual = solicitud.getTipoPrestamo().getTasaInteres() / 100;
        double cuotaBase = monto / plazo;
        double interesMensual = cuotaBase * tasaInteresMensual;
        double cuotaMensual = cuotaBase + interesMensual;

        return new SolicitudCompleta(
                uuidProvider.toString(solicitud.getPublicSolicitudId()),
                solicitud.getMonto(),
                solicitud.getPlazo(),
                usuarioInfo.email(),
                usuarioInfo.nombre(),
                solicitud.getTipoPrestamo().getNombre(),
                solicitud.getTipoPrestamo().getTasaInteres(),
                solicitud.getEstado().getNombre(),
                usuarioInfo.salarioBase(),
                cuotaMensual
        );
    }
}