package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ItineraryResource {

    private Long id;
    private String timeIn;
    private String timeOut;
    private Long nurseId;
}
