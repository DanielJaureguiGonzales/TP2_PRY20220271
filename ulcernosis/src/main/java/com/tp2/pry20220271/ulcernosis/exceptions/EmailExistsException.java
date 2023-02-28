package com.tp2.pry20220271.ulcernosis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailExistsException extends RuntimeException{
    private String message;

    public EmailExistsException(String message) {
        super(message);
    }
}
