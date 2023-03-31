package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Appointment;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.enums.Status;
import com.tp2.pry20220271.ulcernosis.models.repositories.*;
import com.tp2.pry20220271.ulcernosis.models.services.AppointmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAppointmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AppointmentResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final AppointmentRepository appointmentRepository;
    private final MedicRepository medicRepository;
    private final NurseRepository nurseRepository;
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final TeamWorkRepository teamWorkRepository;

    @Override
    public List<AppointmentResource> findAllAppointmentsByNurseId(Long nurseId) {
        List<Appointment> appointments = appointmentRepository.findAllByNurseId(nurseId);
        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentResource.class)).toList();
    }

    @Override
    public AppointmentResource findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment","id",id));
        return modelMapper.map(appointment, AppointmentResource.class);
    }

    @Override
    public AppointmentResource save(SaveAppointmentResource appoinmentResource) {
        Medic medic = medicRepository.findById(appoinmentResource.getMedicId()).orElseThrow(() -> new NotFoundException("Medic","id",appoinmentResource.getMedicId()));
        Nurse nurse = nurseRepository.findById(appoinmentResource.getNurseId()).orElseThrow(() -> new NotFoundException("Nurse","id",appoinmentResource.getNurseId()));
        Patient patient = patientRepository.findById(appoinmentResource.getPatientId()).orElseThrow(() -> new NotFoundException("Patient","id",appoinmentResource.getPatientId()));


        Appointment appointment = new Appointment();
        appointment.setAddress(appoinmentResource.getAddress());
        appointment.setDateAsigDiag(appoinmentResource.getDateAsigDiag());
        appointment.setStatus(Status.PENDIENTE);
        appointment.setMedicId(medic.getId());
        appointment.setNurseId(nurse.getId());
        appointment.setPatientId(patient.getId());

        appointment.setDiagnosisId(0L);
        return modelMapper.map(appointmentRepository.save(appointment), AppointmentResource.class);
    }

    @Override
    public String deleteAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment","id",id));
        appointmentRepository.delete(appointment);
        return """
                    La cita se eliminó correctamente.
                """;
    }
}
