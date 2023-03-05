package com.tp2.pry20220271.ulcernosis.resources.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResource {
    private Long id;

    private String fullName;

    private String email;

    private String dni;
    private String phone;

    private Integer age;

    private String address;
    private String civilStatus;

    private Long medicId;


}
