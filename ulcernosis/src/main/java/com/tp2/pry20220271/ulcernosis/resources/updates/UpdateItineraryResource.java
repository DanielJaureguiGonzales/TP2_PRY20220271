package com.tp2.pry20220271.ulcernosis.resources.updates;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateItineraryResource {

    @NotEmpty(message = "La hora de entrada no puede ser vacío")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9] (am|pm)$")
    private String timeIn;

    @NotEmpty(message = "La hora de salida puede ser vacío")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9] (am|pm)$")
    private String timeOut;
}
