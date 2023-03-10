package com.tp2.pry20220271.ulcernosis.resources.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveScheduleResourceTimeIn {

    private LocalTime time;

    private double latitude;

    private double longitude;

    private Long nurseId;


}
