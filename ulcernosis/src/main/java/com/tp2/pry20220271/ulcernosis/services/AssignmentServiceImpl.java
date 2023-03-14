package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.AssignmentExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.LimitAssignmentExceeded;
import com.tp2.pry20220271.ulcernosis.exceptions.LimitTeamWorkExceeded;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Assignment;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.repositories.AssignmentRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.services.AssignmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    private final NurseRepository nurseRepository;

    private final PatientRepository patientRepository;

    private final TeamWorkRepository teamWorkRepository;

    @Override
    public List<AssignmentResource> findAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentResource> getAssignmentByPatientId(Long patientId) {
        patientRepository.findById(patientId).orElseThrow(() -> new NotFoundException("Patient","id",patientId));
        List<Assignment> assignments = assignmentRepository.findAllByPatientId(patientId);
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentResource> getAssignmentByNurseId(Long nurseId) {
        nurseRepository.findById(nurseId).orElseThrow(() -> new NotFoundException("Nurse","id",nurseId));
        List<Assignment> assignments = assignmentRepository.findAllByNurseId(nurseId);
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());

    }

    @Override
    public AssignmentResource createAssignment(SaveAssignmentResource assignment) {
        Nurse nurse = nurseRepository.findById(assignment.getNurseId()).orElseThrow(() -> new NotFoundException("Nurse","id",assignment.getNurseId()));
        Patient patient = patientRepository.findById(assignment.getPatientId()).orElseThrow(() -> new NotFoundException("Patient","id",assignment.getPatientId()));

        // VALIDAR SI EL PACIENTE YA TIENE UNA ASIGNACION
        if(patient.getIsAssigned()){
            throw new LimitAssignmentExceeded("El paciente ya tiene una asignación con otro enfermero");
        }

        Assignment newAssignment = new Assignment();
        newAssignment.setNurse(nurse);
        newAssignment.setPatient(patient);
        Assignment assignmentCreated = assignmentRepository.save(newAssignment);
        patient.setIsAssigned(true);
        nurseRepository.save(nurse);
        patientRepository.save(patient);
        return modelMapper.map(assignmentCreated, AssignmentResource.class);
    }

    @Override
    public String deleteAssigment(Long id) {
        Assignment assignment = getAssignmentById(id);
        Patient patient = assignment.getPatient();

        assignmentRepository.delete(assignment);
        patient.setIsAssigned(false);
        return "Se eliminó la asignación de forma exitosa";
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Assignment","Id",id)
        );
    }

}
