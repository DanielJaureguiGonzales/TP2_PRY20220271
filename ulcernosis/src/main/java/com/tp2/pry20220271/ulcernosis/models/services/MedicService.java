package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MedicService {

    List<MedicResource> findAll() throws UlcernosisException;
    MedicResource findMedicById(Long id) throws UlcernosisException;

    MedicResource findMedicByFullName(String fullName) throws UlcernosisException;

    Resource findMedicPhoto(Long id) throws UlcernosisException, IOException;

    Resource updateMedicPhoto(Long id, MultipartFile avatar) throws UlcernosisException, IOException;

    MedicResource saveMedic(SaveMedicResource saveMedicResource) throws UlcernosisException, IOException;
    MedicResource updateMedic(Long id, SaveMedicResource saveMedicResource) throws UlcernosisException, IOException;

    String deleteMedic(Long id) throws UlcernosisException;

}
