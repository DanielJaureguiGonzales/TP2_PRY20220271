package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
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

  /*  private static final ModelMapper modelMapper = new ModelMapper();

    private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<AssignmentResource> findAllAssignments() throws UlcernosisException {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentResource> getAssignmentByPatientId(Long patientId) throws UlcernosisException {
        patientRepository.findById(patientId).orElseThrow(() -> new NotFoundException("UCN-404","PATIENT_NOT_FOUND"));
        List<Assignment> assignments = assignmentRepository.findAllByPatientId(patientId);
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentResource> getAssignmentByNurseId(Long nurseId) throws UlcernosisException {
        nurseRepository.findById(nurseId).orElseThrow(() -> new NotFoundException("UCN-404","PATIENT_NOT_FOUND"));
        List<Assignment> assignments = assignmentRepository.findAllByNurseId(nurseId);
        return assignments.stream().map(assignment -> modelMapper.map(assignment, AssignmentResource.class)).collect(Collectors.toList());

    }

    @Override
    public AssignmentResource createAssignment(SaveAssignmentResource assignment) throws UlcernosisException {
        Nurse nurse = nurseRepository.findById(assignment.getNurseId()).orElseThrow(() -> new NotFoundException("UCN-404","NURSE_NOT_FOUND"));
        Patient patient = patientRepository.findById(assignment.getPatientId()).orElseThrow(() -> new NotFoundException("UCN-404","PATIENT_NOT_FOUND"));

        Assignment newAssignment = new Assignment();
        newAssignment.setNurse(nurse);
        newAssignment.setPatient(patient);
        Long id;
        try{
            id = assignmentRepository.save(newAssignment).getId();
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }
        return modelMapper.map(getAssignmentById(id), AssignmentResource.class);
    }

    @Override
    public String deleteAssigment(Long id) throws UlcernosisException {
        Assignment assignment = getAssignmentById(id);
        try{
            assignmentRepository.delete(assignment);
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }
        return "Se eliminó la asignación de forma exitosa";
    }

    public Assignment getAssignmentById(Long id) throws NotFoundException {
        return assignmentRepository.findById(id).orElseThrow(()->
                new NotFoundException("UCN-404","TEAM_WORK_NOT_FOUND")
        );
    }*/

}
