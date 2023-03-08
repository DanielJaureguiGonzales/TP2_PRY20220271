package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.resources.request.SaveScheduleResourceTimeIn;
import com.tp2.pry20220271.ulcernosis.resources.response.ScheduleResource;

import java.util.List;

public interface ScheduleService {

    ScheduleResource saveTimeIn(SaveScheduleResourceTimeIn schedule);
    ScheduleResource saveTimeOut(SaveScheduleResourceTimeIn schedule);
    List<ScheduleResource> findAllSchedulesByNurseId(Long nurseId);
}
