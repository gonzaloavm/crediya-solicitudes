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
@Table("estados")
public class EstadoData {

    @Id
    @Column("estado_id")
    private BigInteger estadoId;
    @Column("public_estado_id")
    private byte[] publicestadoId;
    @Column("cod_estado")
    private String codEstado;
    @Column
    private String nombre;
    @Column
    private String descripcion;

}
