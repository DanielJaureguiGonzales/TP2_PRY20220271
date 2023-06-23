package com.tp2.pry20220271.ulcernosis.resources.request;



import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import lombok.Getter;

import lombok.Setter;



@Getter
@Setter
public class SaveMedicResource {

    private String fullName;


    private String email;

    private String password;


    private String dni;
    private String phone;

    private String age;


    private String address;

    private String cmp;

    private String civilStatus;
    private Role role;
}
