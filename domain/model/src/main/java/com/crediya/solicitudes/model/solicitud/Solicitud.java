package com.crediya.solicitudes.model.solicitud;
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

    private BigInteger id;
    private Double monto;
    private Integer plazo;
    private String email;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private BigInteger idEstado;
    private BigInteger idTipoPrestamo;
}
