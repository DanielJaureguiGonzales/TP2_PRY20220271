package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.services.TeamWorkService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamWorkServiceImpl implements TeamWorkService {

    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger log = LoggerFactory.getLogger(TeamWorkServiceImpl.class);

    @Autowired
    private TeamWorkRepository teamWorkRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public List<TeamWorkResource> findAllTeamWork() throws UlcernosisException {
        List<TeamWork> teamWorks = teamWorkRepository.findAll();
        return teamWorks.stream().map(teamWork -> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());

    }

    @Override
    public List<TeamWorkResource> getTeamWorkByMedicId(Long medicId) throws UlcernosisException {
        List<TeamWork> teamWorks = teamWorkRepository.findAllByMedicId(medicId);
        return teamWorks.stream().map(teamWork-> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<TeamWorkResource> getTeamWorkByNurseId(Long nurseId) throws UlcernosisException {
        List<TeamWork> teamWorks = teamWorkRepository.findAllByNurseId(nurseId);
        return teamWorks.stream().map(teamWork-> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());
    }

    @Override
    public TeamWorkResource createTeamWork(SaveTeamWorkResource teamWork) throws UlcernosisException {
        Medic medic = medicRepository.findById(teamWork.getMedicId()).orElseThrow(()->new NotFoundException("UCN-404","MEDIC_NOT_FOUND"));
        Nurse nurse = nurseRepository.findById(teamWork.getNurseId()).orElseThrow(()->new NotFoundException("UCN-404","NURSE_NOT_FOUND"));
        TeamWork teamWork1 = new TeamWork();
        teamWork1.setMedic(medic);
        teamWork1.setNurse(nurse);
        Long id;
        try {
            id=teamWorkRepository.save(teamWork1).getId();
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }

        return mapper.map(getTeamWorkByID(id), TeamWorkResource.class);


    }

    @Override
    @Transactional
    public String deleteTeamWorkByNurseId(Long nurseId) throws UlcernosisException {
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(()->new NotFoundException("UCN-404","NURSE_NOT_FOUND"));
        try {
            teamWorkRepository.deleteTeamWorkByNurseId(nurse.getId());
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }
        return "Se ha eliminado con éxito";
    }

    public TeamWork getTeamWorkByID(Long id) throws UlcernosisException {
        return teamWorkRepository.findById(id).orElseThrow(()->
                new NotFoundException("UCN-404","TEAM_WORK_NOT_FOUND")
        );
    }
}
