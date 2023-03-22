package com.tp2.pry20220271.ulcernosis.resources.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp2.pry20220271.ulcernosis.models.enums.TypeHour;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveScheduleResourceTime {

    @NotEmpty(message = "La hora no puede ser vacío")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9] (am|pm)$")
    private String time;

    @NotNull(message = "La latitud no puede ser vacío")
    private double latitude;

    @NotNull(message = "La longitud no puede ser vacío")
    private double longitude;

    @Enumerated(EnumType.STRING)
    private TypeHour typeHour;

    @NotNull(message = "El id del enfermero no puede ser vacío")
    private Long nurseId;

    @NotNull(message = "El id del enfermero no puede ser vacío")
    private Long patientId;

}
