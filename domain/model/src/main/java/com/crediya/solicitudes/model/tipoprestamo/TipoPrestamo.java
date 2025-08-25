package com.crediya.solicitudes.model.tipoprestamo;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TipoPrestamo {

    private BigInteger idTipoPrestamo;
    private String nombre;
    private Double montoMinimo;
    private Double montoMaximo;
    private Double tasaInteres;
    private boolean validacionAutomatica;
}
