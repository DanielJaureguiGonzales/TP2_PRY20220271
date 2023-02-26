package com.tp2.pry20220271.ulcernosis.exceptions;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorExcepcion extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Error error;

    public ErrorExcepcion(Error error) {
        super(error.getMensaje());
        this.error = error;
    }

}