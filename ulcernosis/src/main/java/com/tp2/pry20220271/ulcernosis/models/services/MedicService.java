package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateMedicResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MedicService {

    List<MedicResource> findAll() ;
    MedicResource findMedicById(Long id) ;

    MedicResource findMedicByFullName(String fullName);
    MedicResource findMedicByNurseId(Long nurseId);

    Resource findMedicPhoto(Long id);

    Resource updateMedicPhoto(Long id, MultipartFile avatar) throws IOException;

    MedicResource saveMedic(SaveMedicResource saveMedicResource);
    MedicResource updateMedic(Long id, UpdateMedicResource saveMedicResource) ;

    Medic findMedicByCMP(String cmp);

    String deleteMedic(Long id) ;

}
