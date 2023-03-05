package com.tp2.pry20220271.ulcernosis.resources.etc;


import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "El nombre completo no puede ser vacio")
    private String fullName;

    @Email(message = "El email no puede ser vacio")
    @NotEmpty(message = "El email no puede ser vacio")
    private String email;

    @NotEmpty(message = "La contraseña no puede ser vacia")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,100}$",message = "La contraseña debe tener al menos un numero, una letra minuscula, una letra mayuscula, un caracter especial, al menos 6 caracteres hasta 20")
    private String password;

    @NotEmpty(message = "El dni no puede ser vacia")
    @Pattern(regexp = "^[0-9]*$",message = "El dni debe ser solo números")
    private String dni;

    @NotEmpty(message = "El teléfono no puede ser vacia")
    @Pattern(regexp = "^[0-9]*$",message = "El teléfono debe ser solo números")
    private String phone;

    @NotEmpty(message = "La edad no puede ser vacia")
    private String age;

    @NotEmpty(message = "La dirección no puede ser vacia")
    private String address;


    @Pattern(regexp = "^0\\d{5}$|^0$", message = "El código cmp debe comenzar con 0 y tener 6 dígitos, o que empieze con 0")
    private String cmp;

    @Pattern(regexp = "\\d{6}", message = "El código cep debe tener 6 dígitos")
    private String cep;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @NotEmpty(message = "El estado civil no puede ser vacio")
    private String civilStatus;
}
