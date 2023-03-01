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

    List<AssignmentResource> findAllAssignments();

    List<AssignmentResource> getAssignmentByPatientId(Long patientId);
    List<AssignmentResource> getAssignmentByNurseId(Long nurseId);


    AssignmentResource createAssignment(SaveAssignmentResource assignment);

    String deleteAssigment(Long id);
    //String deleteAssignmentByNurseId(Long nurseId) throws UlcernosisException;
}
