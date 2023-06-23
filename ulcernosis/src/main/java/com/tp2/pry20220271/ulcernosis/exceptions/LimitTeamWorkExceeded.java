package com.tp2.pry20220271.ulcernosis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class LimitTeamWorkExceeded extends RuntimeException{
    private String message;
    public LimitTeamWorkExceeded(String message) {
        super(message);
    }
}
