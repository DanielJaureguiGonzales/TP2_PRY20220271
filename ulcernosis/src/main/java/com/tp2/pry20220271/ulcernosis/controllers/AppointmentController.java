package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.models.services.AppointmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAppointmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AppointmentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurse/{nurseId}")
    private List<AppointmentResource> findAllAppointments(@PathVariable(name = "nurseId") Long nurseId){
        return appointmentService.findAllAppointmentsByNurseId(nurseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private AppointmentResource findById(@PathVariable(name = "id") Long id){
        return appointmentService.findById(id);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-appointment")
    private AppointmentResource save(@RequestBody SaveAppointmentResource saveAppointmentResource){
        return appointmentService.save(saveAppointmentResource);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete-appointment/{id}")
    private String deleteAppointmentById(@PathVariable(name = "id") Long id){
        return appointmentService.deleteAppointmentById(id);
    }

}
