package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResource {
    private String fullName;

    private String email;

    private String dni;

    private String phone;

    private String age;

    private String address;

    private String cmp;

    private String cep;

    private Role role;

    private String civilStatus;
}
