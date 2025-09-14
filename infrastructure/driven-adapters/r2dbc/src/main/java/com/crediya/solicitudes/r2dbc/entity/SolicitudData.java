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
public class SolicitudData {

    @Id
    @Column("id_solicitud")
    private BigInteger id;
    @Column
    private Double monto;
    @Column
    private Integer plazo;
    @Column("documento_identidad")
    private String documentoIdentidad;
    @Column("id_estado")
    private BigInteger idEstado;
    @Column("id_tipo_prestamo")
    private BigInteger idTipoPrestamo;
    @Column("usuario_external_id")
    private String usuarioExternalId;
}