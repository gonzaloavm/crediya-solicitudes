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
@Table("tipos_prestamo")
public class TipoPrestamoData {

    @Id
    @Column("tipo_prestamo_id")
    private BigInteger tipoPrestamoId;
    @Column("public_tipo_prestamo_id")
    private byte[] publicTipoPrestamoId;
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
