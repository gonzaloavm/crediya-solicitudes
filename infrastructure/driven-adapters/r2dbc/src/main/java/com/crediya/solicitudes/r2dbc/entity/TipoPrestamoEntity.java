package com.crediya.solicitudes.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("tipo_prestamo")
public class TipoPrestamoEntity {

    @Id
    @Column("id_tipo_prestamo")
    private BigInteger idTipoPrestamo;

    @Column
    private String nombre;

    @Column("monto_minimo")
    private Double montoMinimo;

    @Column("monto_maximo")
    private Double montoMaximo;

    @Column("tasa_interes")
    private Double tasaInteres;

    @Column("validacion_automatica")
    private boolean validacionAutomatica;
}
