package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    List<AssignmentResource> findAllAssignments() throws UlcernosisException;

    List<AssignmentResource> getAssignmentByPatientId(Long patientId) throws UlcernosisException;
    List<AssignmentResource> getAssignmentByNurseId(Long nurseId) throws UlcernosisException;


    AssignmentResource createAssignment(SaveAssignmentResource assignment) throws UlcernosisException;

    String deleteAssigment(Long id) throws UlcernosisException;
    //String deleteAssignmentByNurseId(Long nurseId) throws UlcernosisException;
}
