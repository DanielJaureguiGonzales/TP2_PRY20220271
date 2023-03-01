package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Assignment;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.repositories.AssignmentRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.services.AssignmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;

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

        Assignment newAssignment = new Assignment();
        newAssignment.setNurse(nurse);
        newAssignment.setPatient(patient);

        return modelMapper.map(assignmentRepository.save(newAssignment), AssignmentResource.class);
    }

    @Override
    public String deleteAssigment(Long id) {
        Assignment assignment = getAssignmentById(id);

        assignmentRepository.delete(assignment);

        return "Se eliminó la asignación de forma exitosa";
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Assignment","Id",id)
        );
    }

}
