package com.tp2.pry20220271.ulcernosis.exceptions;

public enum Error {

    INTERNAL_SERVER_ERROR(1, "INTERNAL SERVER ERROR");

    private final int codError;

    private final String mensaje;

    Error(int codError, String mensaje) {
        this.codError = codError;
        this.mensaje = mensaje;
    }

    public int getCodError() {
        return this.codError;
    }

    public String getMensaje() {
        return this.mensaje;
    }

}