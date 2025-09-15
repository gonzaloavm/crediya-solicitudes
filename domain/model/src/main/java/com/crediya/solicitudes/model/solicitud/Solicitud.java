package com.crediya.solicitudes.model.solicitud;
import com.crediya.solicitudes.model.estado.Estado;
import com.crediya.solicitudes.model.tipoprestamo.TipoPrestamo;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Solicitud {

    private BigInteger idSolicitud;
    private Double monto;
    private Integer plazo;
    private String documentoIdentidad;
    private Estado estado;
    private TipoPrestamo tipoPrestamo;
    private String usuarioExternalId;
}
