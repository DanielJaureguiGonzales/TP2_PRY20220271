package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.services.TeamWorkService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamWorkServiceImpl implements TeamWorkService {

    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger log = LoggerFactory.getLogger(TeamWorkServiceImpl.class);


    private final TeamWorkRepository teamWorkRepository;


    private final MedicRepository medicRepository;


    private final NurseRepository nurseRepository;

    @Override
    public List<TeamWorkResource> findAllTeamWork(){
        List<TeamWork> teamWorks = teamWorkRepository.findAll();
        return teamWorks.stream().map(teamWork -> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());

    }

    @Override
    public List<TeamWorkResource> getTeamWorkByMedicId(Long medicId){
        List<TeamWork> teamWorks = teamWorkRepository.findAllByMedicId(medicId);
        return teamWorks.stream().map(teamWork-> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<TeamWorkResource> getTeamWorkByNurseId(Long nurseId){
        List<TeamWork> teamWorks = teamWorkRepository.findAllByNurseId(nurseId);
        return teamWorks.stream().map(teamWork-> mapper.map(teamWork,TeamWorkResource.class)).collect(Collectors.toList());
    }

    @Override
    public TeamWorkResource createTeamWork(SaveTeamWorkResource teamWork){
        Medic medic = medicRepository.findById(teamWork.getMedicId()).orElseThrow(()->new NotFoundException("Medic","id",teamWork.getMedicId()));
        Nurse nurse = nurseRepository.findById(teamWork.getNurseId()).orElseThrow(()->new NotFoundException("Nurse","id",teamWork.getNurseId()));
        TeamWork teamWork1 = new TeamWork();
        teamWork1.setMedic(medic);
        teamWork1.setNurse(nurse);

        return mapper.map(teamWorkRepository.save(teamWork1), TeamWorkResource.class);


    }

    @Override
    @Transactional
    public String deleteTeamWorkByNurseId(Long nurseId){
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(()->new NotFoundException("Nurse","id",nurseId));
        teamWorkRepository.deleteTeamWorkByNurseId(nurse.getId());
        return "Se ha eliminado con éxito";
    }

    public TeamWork getTeamWorkByID(Long id){
        return teamWorkRepository.findById(id).orElseThrow(()->
                new NotFoundException("TeamWork","id",id)
        );
    }
}
