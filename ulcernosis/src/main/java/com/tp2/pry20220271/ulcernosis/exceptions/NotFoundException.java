package com.tp2.pry20220271.ulcernosis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con este %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
