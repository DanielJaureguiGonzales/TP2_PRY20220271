package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.models.services.ScheduleService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveScheduleResourceTime;
import com.tp2.pry20220271.ulcernosis.resources.response.ScheduleResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    ScheduleResource saveTime(@Valid @RequestBody SaveScheduleResourceTime schedule) {
        return scheduleService.saveTime(schedule);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_all_nurse/{nurseId}")
    List<ScheduleResource> getAllNurse(@PathVariable("nurseId") Long nurseId) {
        return scheduleService.findAllSchedulesByNurseId(nurseId);
    }
}
