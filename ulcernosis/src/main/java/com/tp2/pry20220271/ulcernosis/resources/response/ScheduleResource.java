package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.TypeHour;

import java.time.LocalTime;

public class ScheduleResource {
    private LocalTime time;

    private double latitude;

    private double longitude;
    private TypeHour typeHour;
    private Long nurseId;
}
