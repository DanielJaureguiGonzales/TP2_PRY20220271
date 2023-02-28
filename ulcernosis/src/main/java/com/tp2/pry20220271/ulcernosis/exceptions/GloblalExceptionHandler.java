package com.tp2.pry20220271.ulcernosis.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GloblalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResource> handleNotFoundException(NotFoundException ex, WebRequest request){
        ErrorResource error = new ErrorResource(LocalDateTime.now(),ex.getMessage(),request.getDescription(false), "USER_NOT_FOUND");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorResource> handleEmailExistsException(EmailExistsException ex, WebRequest request){
        ErrorResource error = new ErrorResource(LocalDateTime.now(),ex.getMessage(),request.getDescription(false), "EMAIL_EXISTS");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CmpExistsException.class)
    public ResponseEntity<ErrorResource> handleCMPException(CmpExistsException ex, WebRequest request){
        ErrorResource error = new ErrorResource(LocalDateTime.now(),ex.getMessage(),request.getDescription(false), "CMP_EXISTS");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CepExistsException.class)
    public ResponseEntity<ErrorResource> handleCEPException(CepExistsException ex, WebRequest request){
        ErrorResource error = new ErrorResource(LocalDateTime.now(),ex.getMessage(),request.getDescription(false), "CEP_EXISTS");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResource> handleGlobalException(Exception ex, WebRequest request){
        ErrorResource error = new ErrorResource(LocalDateTime.now(),ex.getMessage(),request.getDescription(false), "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
