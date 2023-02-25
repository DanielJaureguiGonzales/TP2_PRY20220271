package com.tp2.pry20220271.ulcernosis.resources.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import lombok.Setter;



@Getter
@Setter
public class SaveMedicResource {

    private String fullName;
    private String email;
    private String password;

    private String dni;

    private Integer age;
    private String address;
    private String cmp;
    private String civilStatus;
}
