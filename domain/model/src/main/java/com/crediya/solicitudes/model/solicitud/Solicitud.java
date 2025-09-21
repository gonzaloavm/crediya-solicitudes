package com.crediya.solicitudes.model.solicitud;
import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Solicitud {

    private BigInteger solicitudId;
    private byte[] publicSolicitudId;
    private Double monto;
    private Integer plazo;
    private String documentoIdentidad;
    private Estado estado;
    private TipoPrestamo tipoPrestamo;
    private String usuarioExternalId;
}
