package com.tp2.pry20220271.ulcernosis.resources.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavePatientResource {

    @NotEmpty(message = "El nombre completo no puede ser vacio")
    private String fullName;

    @Email(message = "El email no puede ser vacio")
    private String email;

    @NotEmpty(message = "El dni no puede ser vacia")
    @Pattern(regexp = "\\d{8}",message = "El dni debe tener 8 dígitos y debe ser solo números")
    private String dni;

    @NotEmpty(message = "El teléfono no puede ser vacia")
    @Pattern(regexp = "\\d{9}",message = "El teléfono debe tener 9 dígitos y debe ser solo números")
    private String phone;

    @NotNull(message = "La edad no puede ser vacia")
    @Max(value = 99, message = "La edad debe ser menor a 99")
    private Integer age;

    @NotEmpty(message = "La dirección no puede ser vacia")
    private String address;

    @NotEmpty(message = "El estado civil no puede ser vacio")
    private String civilStatus;




}
