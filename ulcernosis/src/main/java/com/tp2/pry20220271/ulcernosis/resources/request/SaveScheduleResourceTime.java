package com.tp2.pry20220271.ulcernosis.resources.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp2.pry20220271.ulcernosis.models.enums.TypeHour;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveScheduleResourceTime {
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime time;

    @NotNull(message = "La latitud no puede ser vacío")
    private double latitude;

    @NotNull(message = "La longitud no puede ser vacío")
    private double longitude;

    @Enumerated(EnumType.STRING)
    private TypeHour typeHour;

    private Long nurseId;


}
