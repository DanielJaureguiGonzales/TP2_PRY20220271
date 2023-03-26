package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.TypeHour;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class ScheduleResource {
    private String time;

    private double latitude;

    private double longitude;
    private TypeHour typeHour;
    private Long nurseId;

    private String patientName;
    private Date date;
}
