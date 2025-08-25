package com.crediya.solicitudes.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("solicitudes")
public class SolicitudEntity {

    @Id
    @Column("id_solicitud")
    private BigInteger id;

    @Column
    private Double monto;

    @Column
    private Integer plazo;

    @Column
    private String email;

    @Column("fecha_creacion")
    private LocalDate fechaCreacion;

    @Column("fecha_actualizacion")
    private LocalDate fechaActualizacion;

    // Relaciones: se manejan con las claves foráneas
    @Column("id_estado")
    private BigInteger idEstado;

    @Column("id_tipo_prestamo")
    private BigInteger idTipoPrestamo;
}