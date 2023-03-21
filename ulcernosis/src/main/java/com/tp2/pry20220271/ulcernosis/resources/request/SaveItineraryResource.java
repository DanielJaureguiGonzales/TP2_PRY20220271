package com.tp2.pry20220271.ulcernosis.resources.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class SaveItineraryResource {


    @NotNull
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeIn;

    @NotNull
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeOut;

    private Long nurseId;
}
