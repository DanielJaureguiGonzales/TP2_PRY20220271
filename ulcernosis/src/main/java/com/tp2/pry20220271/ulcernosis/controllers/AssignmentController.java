package com.tp2.pry20220271.ulcernosis.controllers;


import com.tp2.pry20220271.ulcernosis.models.services.AssignmentService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveAssignmentResource;
import com.tp2.pry20220271.ulcernosis.resources.response.AssignmentResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AssignmentResource> findAllTeamWork(){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.findAllAssignments());
        return assignmentService.findAllAssignments();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/patient/{patientId}")
    public List<AssignmentResource> getTeamWorkByPatientId(@PathVariable("patientId") Long patientId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.getAssignmentByPatientId(patientId));
        return assignmentService.getAssignmentByPatientId(patientId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurses/{nurseId}")
    public List<AssignmentResource> getAssignmentByNurseId(@PathVariable("nurseId") Long nurseId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",assignmentService.getAssignmentByNurseId(nurseId));
        return assignmentService.getAssignmentByNurseId(nurseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-assigment")
    public AssignmentResource createAssignment(@Valid @RequestBody SaveAssignmentResource assignment){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",assignmentService.createAssignment(assignment));
        return assignmentService.createAssignment(assignment);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{assignmentId}/delete-assignment")
    public String deleteAssignment(@PathVariable("assignmentId") Long assignmentId){
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", assignmentService.deleteAssigment(assignmentId));
        return assignmentService.deleteAssigment(assignmentId);
    }


}
