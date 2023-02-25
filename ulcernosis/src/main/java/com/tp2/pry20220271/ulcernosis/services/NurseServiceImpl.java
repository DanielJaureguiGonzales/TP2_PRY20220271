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
import com.tp2.pry20220271.ulcernosis.models.services.NurseService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;

import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NurseServiceImpl implements NurseService {

    private static final Logger log = LoggerFactory.getLogger(MedicServiceImpl.class);
    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private TeamWorkRepository teamWorkRepository;

    @Override
    public List<NurseResource> findAll() throws UlcernosisException {
        List<Nurse> nurses = nurseRepository.findAll();
        return nurses.stream().map(m -> mapper.map(m,NurseResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<NurseResource> findAllByMedicId(Long medicalId) throws UlcernosisException {
        Medic medic = medicRepository.findById(medicalId).orElseThrow(()-> new NotFoundException("UCN-404","MEDIC_NOT_FOUND"));
        List<TeamWork> teamWorks = teamWorkRepository.findAllByMedicId(medicalId);
        List<Nurse> nurseList = nurseRepository.findAllByTeamWorkIn(teamWorks);
        return nurseList.stream().map(nurse -> mapper.map(nurse,NurseResource.class)).collect(Collectors.toList());
    }


    @Override
    public NurseResource findNurseById(Long id) throws UlcernosisException {
        return mapper.map(getNurseByID(id), NurseResource.class);
    }

    @Override
    public Resource findNursePhoto(Long id) throws UlcernosisException {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","USER_NOT_FOUND"));
        Resource avatar = new ByteArrayResource(nurse.getAvatar());
        return avatar;
    }

    @Override
    public Resource putNursePhoto(Long id, MultipartFile file) throws UlcernosisException {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","USER_NOT_FOUND"));
        try {
            nurse.setAvatar(file.getBytes());
            nurseRepository.save(nurse);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        Resource avatar = new ByteArrayResource(nurse.getAvatar());
        return avatar;

    }

    @Override
    public NurseResource saveNurse(SaveNurseResource saveNurseResource) throws UlcernosisException, IOException {
        Nurse newNurse = mapper.map(saveNurseResource,Nurse.class);
        newNurse.setDni(String.format("%08d",Integer.valueOf(saveNurseResource.getDni())));
        newNurse.setAvatar(new byte[]{});

        Long id;
        try {
            id=nurseRepository.save(newNurse).getId();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        NurseResource nurseResource = mapper.map(getNurseByID(id), NurseResource.class);

        return nurseResource;
    }

    @Override
    public NurseResource updateNurse(Long id, SaveNurseResource saveNurseResource) throws UlcernosisException, IOException {
        Nurse updateNurse = getNurseByID(id);
        updateNurse.setDni(saveNurseResource.getDni());
        updateNurse.setAge(saveNurseResource.getAge());
        updateNurse.setEmail(saveNurseResource.getEmail());
        updateNurse.setCivilStatus(saveNurseResource.getCivilStatus());
        updateNurse.setFullName(saveNurseResource.getFullName());
        updateNurse.setCep(saveNurseResource.getCep());
        updateNurse.setAddress(saveNurseResource.getAddress());

        Long idUpdatedNurse;
        try {
            idUpdatedNurse = nurseRepository.save(updateNurse).getId();
        }catch (Exception e) {
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }

        NurseResource nurseResource = mapper.map(getNurseByID(idUpdatedNurse), NurseResource.class);
        return nurseResource;
    }

    @Override
    public String deleteNurse(Long id) throws UlcernosisException {
        Nurse deleteNurse = getNurseByID(id);

        try{
            nurseRepository.delete(deleteNurse);
        }catch (Exception e){
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        return "Se eliminó al enfermero exitosamente";
    }

    public Nurse getNurseByID(Long id) throws UlcernosisException {
        return nurseRepository.findById(id).orElseThrow(()->
                new NotFoundException("UCN-404","USER_NOT_FOUND")
        );
    }
}
