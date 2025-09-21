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
@Table("solicitudes")
public class SolicitudData {

    @Id
    @Column("solicitud_id")
    private BigInteger solicitudId;
    @Column("public_solicitud_id")
    private byte[] publicSolicitudId;
    @Column
    private Double monto;
    @Column
    private Integer plazo;
    @Column("documento_identidad")
    private String documentoIdentidad;
    @Column("estado_id")
    private BigInteger estadoId;
    @Column("tipo_prestamo_id")
    private BigInteger tipoPrestamoId;
    @Column("usuario_external_id")
    private String usuarioExternalId;

}