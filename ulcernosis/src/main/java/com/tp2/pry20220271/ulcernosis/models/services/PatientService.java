package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;

import com.tp2.pry20220271.ulcernosis.resources.request.SavePatientResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import com.tp2.pry20220271.ulcernosis.resources.response.PatientResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PatientService {

    //List<PatientResource> findAllPatientsByCreatorId(Long creatorId) throws UlcernosisException;
    /*List<PatientResource> findAllPatientsByMedicId(Long medicId) throws UlcernosisException;

    List<PatientResource> findAllByNurseId(Long nurseId) throws UlcernosisException;
    PatientResource findPatientById(Long id) throws UlcernosisException;
    Resource findPatientPhoto(Long id) throws UlcernosisException;
    Resource putPatientPhoto(Long id, MultipartFile file) throws UlcernosisException, IOException;
    PatientResource createPatient(SavePatientResource savePatientResource) throws UlcernosisException, IOException;

    PatientResource updatePatient(SavePatientResource savePatientResource, Long patientId) throws UlcernosisException, IOException;
    String deletePatient(Long patientId) throws UlcernosisException;*/
}
