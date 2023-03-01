package com.tp2.pry20220271.ulcernosis.controllers;


import com.tp2.pry20220271.ulcernosis.models.services.TeamWorkService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-works")
public class TeamWorkController {

    @Autowired
    private TeamWorkService teamWorkService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TeamWorkResource> findAllTeamWork() {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.findAllTeamWork());
        return teamWorkService.findAllTeamWork();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/medic/{medicId}")
    public List<TeamWorkResource> getTeamWorkByMedicId(@PathVariable("medicId") Long medicId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.getTeamWorkByMedicId(medicId));
        return teamWorkService.getTeamWorkByMedicId(medicId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurse/{nurseId}")
    public List<TeamWorkResource> getTeamWorkByNurseId(@PathVariable("nurseId") Long nurseId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",teamWorkService.getTeamWorkByNurseId(nurseId));
        return teamWorkService.getTeamWorkByNurseId(nurseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-team-work")
    public TeamWorkResource createTeamWork(@Valid @RequestBody SaveTeamWorkResource teamWork) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",teamWorkService.createTeamWork(teamWork));
        return teamWorkService.createTeamWork(teamWork);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete-by-nurse/{nurseId}")
    public String deleteTeamWorkByNurseId(@PathVariable("nurseId") Long nurseId) {
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", teamWorkService.deleteTeamWorkByNurseId(nurseId));
        return teamWorkService.deleteTeamWorkByNurseId(nurseId);
    }

}
