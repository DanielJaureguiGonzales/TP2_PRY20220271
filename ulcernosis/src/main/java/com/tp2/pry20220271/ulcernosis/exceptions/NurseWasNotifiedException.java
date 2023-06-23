package com.tp2.pry20220271.ulcernosis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NurseWasNotifiedException extends RuntimeException{
    private String message;
    public NurseWasNotifiedException(String message) {
        super(message);
    }
}
