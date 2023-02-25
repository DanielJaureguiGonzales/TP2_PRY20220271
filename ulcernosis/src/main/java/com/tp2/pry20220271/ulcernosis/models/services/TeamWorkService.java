package com.tp2.pry20220271.ulcernosis.models.services;


import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamWorkService {


    List<TeamWorkResource> findAllTeamWork() throws UlcernosisException;

    List<TeamWorkResource> getTeamWorkByMedicId(Long medicId) throws UlcernosisException;
    List<TeamWorkResource> getTeamWorkByNurseId(Long nurseId) throws UlcernosisException;


    TeamWorkResource createTeamWork(SaveTeamWorkResource teamWork) throws UlcernosisException;
    String deleteTeamWorkByNurseId(Long nurseId) throws UlcernosisException;
}
