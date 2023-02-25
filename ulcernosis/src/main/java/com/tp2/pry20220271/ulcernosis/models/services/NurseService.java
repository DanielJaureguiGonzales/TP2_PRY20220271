package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NurseService {

    List<NurseResource> findAll() throws UlcernosisException;

    List<NurseResource> findAllByMedicId(Long medicalId) throws UlcernosisException;

    NurseResource findNurseById(Long id) throws UlcernosisException;

    Resource findNursePhoto(Long id) throws UlcernosisException;
    Resource putNursePhoto(Long id, MultipartFile file) throws UlcernosisException;
    NurseResource saveNurse(SaveNurseResource saveNurseResource) throws UlcernosisException, IOException;
    NurseResource updateNurse(Long id, SaveNurseResource saveNurseResource) throws UlcernosisException, IOException;

    String deleteNurse(Long id) throws UlcernosisException;

}
