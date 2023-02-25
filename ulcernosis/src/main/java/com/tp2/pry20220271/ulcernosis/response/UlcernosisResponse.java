package com.tp2.pry20220271.ulcernosis.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UlcernosisResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;
    private String code;
    private String message;
    private T data;

    // CREAMOS LOS CUERPOS DE RESPUESTA TIPO JSON, DEPENDIENDO AL TIPO DE RESPUESTA YA SEA UN "GET, DELETE" O "POST, PUT"
    public UlcernosisResponse(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public UlcernosisResponse(String status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
