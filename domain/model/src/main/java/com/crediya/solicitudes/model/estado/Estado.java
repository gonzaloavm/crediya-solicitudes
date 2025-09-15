package com.crediya.solicitudes.model.estado;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Estado {
    private BigInteger idEstado;
    private String codEstado;
    private String nombre;
    private String descripcion;
}
