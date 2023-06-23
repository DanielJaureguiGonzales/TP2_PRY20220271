package com.tp2.pry20220271.ulcernosis.models.services;


import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;

import java.util.List;

public interface TeamWorkService {


    List<TeamWorkResource> findAllTeamWork();

    List<TeamWorkResource> getTeamWorkByMedicId(Long medicId);
    List<TeamWorkResource> getTeamWorkByNurseId(Long nurseId);


    TeamWorkResource createTeamWork(SaveTeamWorkResource teamWork);
    String deleteTeamWorkByNurseId(Long nurseId);
}
