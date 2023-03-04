package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;

import java.util.List;

public interface AssignmentService {

    List<AssignmentResource> findAllAssignments();

    List<AssignmentResource> getAssignmentByPatientId(Long patientId);
    List<AssignmentResource> getAssignmentByNurseId(Long nurseId);


    AssignmentResource createAssignment(SaveAssignmentResource assignment);

    String deleteAssigment(Long id);
    //String deleteAssignmentByNurseId(Long nurseId) throws UlcernosisException;
}
