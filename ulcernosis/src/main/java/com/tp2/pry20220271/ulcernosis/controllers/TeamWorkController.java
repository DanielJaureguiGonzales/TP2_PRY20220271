package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.services.PatientService;
import com.tp2.pry20220271.ulcernosis.models.services.TeamWorkService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import com.tp2.pry20220271.ulcernosis.response.UlcernosisResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ulcernosis")
public class TeamWorkController {

    @Autowired
    private TeamWorkService teamWorkService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/team-works")
    public List<TeamWorkResource> findAllTeamWork() throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.findAllTeamWork());
        return teamWorkService.findAllTeamWork();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/team-works/medics/{medicId}")
    public List<TeamWorkResource> getTeamWorkByMedicId(@PathVariable("medicId") Long medicId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.getTeamWorkByMedicId(medicId));
        return teamWorkService.getTeamWorkByMedicId(medicId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/team-works/nurses/{nurseId}")
    public List<TeamWorkResource> getTeamWorkByNurseId(@PathVariable("nurseId") Long nurseId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.getTeamWorkByNurseId(nurseId));
        return teamWorkService.getTeamWorkByNurseId(nurseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/team-works/create")
    public TeamWorkResource createTeamWork(@Valid @RequestBody SaveTeamWorkResource teamWork) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",teamWorkService.createTeamWork(teamWork));
        return teamWorkService.createTeamWork(teamWork);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/team-works/delete/nurse/{nurseId}")
    public String deleteTeamWorkByNurseId(@PathVariable("nurseId") Long nurseId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", teamWorkService.deleteTeamWorkByNurseId(nurseId));
        return teamWorkService.deleteTeamWorkByNurseId(nurseId);
    }


}
