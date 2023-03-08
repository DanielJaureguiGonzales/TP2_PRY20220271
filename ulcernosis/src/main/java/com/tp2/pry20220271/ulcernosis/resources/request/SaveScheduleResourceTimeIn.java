package com.tp2.pry20220271.ulcernosis.resources.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveScheduleResourceTimeIn {

    private Date time;

    private String latitude;

    private String longitude;

    private String altitude;

    private Long nurseId;


}
