package com.tp2.pry20220271.ulcernosis.controllers;


import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.services.AssignmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import com.tp2.pry20220271.ulcernosis.response.UlcernosisResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ulcernosis")
public class AssignmentController {

   /* @Autowired
    private AssignmentService assignmentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/assignments")
    public List<AssignmentResource> findAllTeamWork() throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.findAllAssignments());
        return assignmentService.findAllAssignments();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/assignments/patient/{patientId}")
    public List<AssignmentResource> getTeamWorkByPatientId(@PathVariable("patientId") Long patientId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.getAssignmentByPatientId(patientId));
        return assignmentService.getAssignmentByPatientId(patientId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/assignments/nurses/{nurseId}")
    public List<AssignmentResource> getAssignmentByNurseId(@PathVariable("nurseId") Long nurseId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.getAssignmentByNurseId(nurseId));
        return assignmentService.getAssignmentByNurseId(nurseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/assignments/create")
    public AssignmentResource createAssignment(@Valid @RequestBody SaveAssignmentResource assignment) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",assignmentService.createAssignment(assignment));
        return assignmentService.createAssignment(assignment);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/assignments/delete/{assignmentId}")
    public String deleteAssignment(@PathVariable("assignmentId") Long assignmentId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", assignmentService.deleteAssigment(assignmentId));
        return assignmentService.deleteAssigment(assignmentId);
    }
*/


}
