package com.tp2.pry20220271.ulcernosis.models.services;


import com.tp2.pry20220271.ulcernosis.resources.request.SaveAppointmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AppointmentResource;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResource> findAllAppointmentsByNurseId(Long nurseId);
    AppointmentResource findById(Long id);
    AppointmentResource save(SaveAppointmentResource appoinmentResource);

    String deleteAppointmentById(Long id);

}
