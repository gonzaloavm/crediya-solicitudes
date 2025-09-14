package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.r2dbc.entity.SolicitudData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;

public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudData, BigInteger>, ReactiveQueryByExampleExecutor<SolicitudData> {

}
