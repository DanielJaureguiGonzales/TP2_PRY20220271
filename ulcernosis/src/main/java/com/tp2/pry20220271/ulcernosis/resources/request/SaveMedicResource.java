package com.tp2.pry20220271.ulcernosis.resources.request;



import jakarta.validation.constraints.*;
import lombok.Getter;

import lombok.Setter;



@Getter
@Setter
public class SaveMedicResource {
    @NotEmpty(message = "El nombre completo no puede ser vacio")
    private String fullName;

    @Email(message = "El email es inválido")
    @NotEmpty(message = "El email no puede ser vacio")
    private String email;

    @NotEmpty(message = "La contraseña no puede ser vacia")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,100}$",message = "La contraseña debe tener al menos un numero, una letra minuscula, una letra mayuscula, un caracter especial, al menos 6 caracteres hasta 20")
    private String password;

    @NotEmpty(message = "El dni no puede ser vacia")
    @Pattern(regexp = "^[0-9]*$",message = "DNI must be only numbers")
    private String dni;

    @NotEmpty(message = "La edad no puede ser vacia")
    private String age;

    @NotEmpty(message = "La dirección no puede ser vacia")
    private String address;

    @NotEmpty(message = "El cmp no puede ser vacio")
    private String cmp;

    @NotEmpty(message = "El estado civil no puede ser vacio")
    private String civilStatus;
}
