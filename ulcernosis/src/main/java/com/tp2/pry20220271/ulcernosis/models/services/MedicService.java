package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MedicService {

    List<MedicResource> findAll() ;
    MedicResource findMedicById(Long id) ;

    MedicResource findMedicByFullName(String fullName);

    Resource findMedicPhoto(Long id);

    Resource updateMedicPhoto(Long id, MultipartFile avatar) throws IOException;

    MedicResource saveMedic(SaveMedicResource saveMedicResource);
    MedicResource updateMedic(Long id, SaveMedicResource saveMedicResource) ;

    Medic findMedicByCMP(String cmp);

    String deleteMedic(Long id) ;

}
