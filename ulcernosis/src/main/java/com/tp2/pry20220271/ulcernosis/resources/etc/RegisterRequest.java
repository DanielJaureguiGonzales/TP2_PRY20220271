package com.tp2.pry20220271.ulcernosis.resources.etc;


import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String dni;
    private Integer age;
    private String address;
    private String cmp;
    private String cep;
    private Rol rol;
    private String civilStatus;
}
