package com.crediya.solicitudes.model.tipoprestamo.exception;

public class TipoPrestamoNoEncontradoException extends RuntimeException {
    public TipoPrestamoNoEncontradoException(String mensaje) {
        super("El tipo de préstamo no fue encontrado: " + mensaje);
    }
}
