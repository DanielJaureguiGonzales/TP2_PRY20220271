package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NurseService {

    List<NurseResource> findAll();

    List<NurseResource> findAllByMedicId(Long medicalId) ;
    List<NurseResource> findAllByHaveTeamWork(Boolean isHaveTeamWork);
    List<NurseResource> findAllByIsAuxiliar(Boolean isAuxiliar);
    NurseResource findNurseById(Long id);

    Nurse findNurseByCEP(String cep);

   Resource findNursePhoto(Long id);
    Resource putNursePhoto(Long id, MultipartFile file) throws IOException;
    NurseResource saveNurse(SaveNurseResource saveNurseResource);
    NurseResource updateNurse(Long id, SaveNurseResource saveNurseResource) ;

    String deleteNurse(Long id);

}
