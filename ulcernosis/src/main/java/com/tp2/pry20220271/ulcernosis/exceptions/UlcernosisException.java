package com.tp2.pry20220271.ulcernosis.exceptions;

import java.util.ArrayList;
import java.util.List;

public class UlcernosisException extends Exception{

    private static final long serialVersionUID = 1L;

    private final String code;
    private final int responseCode;
    private final List<ErrorResource> errorList = new ArrayList<>();

    public UlcernosisException(String code, int responseCode,String message, List<ErrorResource> errorList) {
        super(message);
        this.code = code;
        this.responseCode = responseCode;
        this.errorList.addAll(errorList);
    }

    public UlcernosisException(String code,  int responseCode,String message) {
        super(message);
        this.code = code;
        this.responseCode = responseCode;
    }

    public String getCode() {
        return code;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<ErrorResource> getErrorList() {
        return errorList;
    }
}
