package com.tp2.pry20220271.ulcernosis.resources.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SaveNurseResource {
    private String fullName;

    private String email;
    private String password;

    private String dni;

    private String phone;
    private Integer age;

    private String civilStatus;
    private String address;
    private String cep;


}
