package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.resources.request.SavePatientResource;
import com.tp2.pry20220271.ulcernosis.resources.response.PatientResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PatientService {

    //List<PatientResource> findAllPatientsByCreatorId(Long creatorId) throws UlcernosisException;
    List<PatientResource> findAllPatientsByMedicId(Long medicId);

    /*List<PatientResource> findAllByNurseId(Long nurseId);*/
    PatientResource findPatientById(Long id);
    Resource findPatientPhoto(Long id);
    Resource putPatientPhoto(Long id, MultipartFile file) throws IOException;
    PatientResource createPatient(SavePatientResource savePatientResource, Long medicId);

    PatientResource updatePatient(SavePatientResource savePatientResource, Long patientId);
    String deletePatient(Long patientId);

    /*List<PatientResource> findAllByAssigned(Long medicId,Boolean isAssigned);*/

    List<PatientResource> findAllPatientsByMedicIdAndIsAssigned(Long medicId, Boolean isAssigned);
    List<PatientResource> findAllPatientsByNurseIdAndIsAssigned(Long nurseId);
}
