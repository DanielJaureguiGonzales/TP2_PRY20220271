package com.tp2.pry20220271.ulcernosis.resources.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseResource {

    private Long id;
    private String fullName;
    private String email;

    private String dni;

    private Integer age;
    private String address;
    private String cep;


}