package com.tp2.pry20220271.ulcernosis.resources.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveItineraryResource {


    @NotNull(message = "La hora de entrada no puede ser vacío")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3])(:)([0-5][0-9]) (am|pm)$")
    private String timeIn;

    @NotEmpty(message = "La hora de salida no puede ser vacío")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3])(:)([0-5][0-9]) (am|pm)$")
    private String timeOut;


}
