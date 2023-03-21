package com.tp2.pry20220271.ulcernosis.resources.updates;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateItineraryResource {

    @NotNull
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeIn;
    @NotNull
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeOut;
}
