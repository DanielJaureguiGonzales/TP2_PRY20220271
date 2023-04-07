package com.tp2.pry20220271.ulcernosis.models.services;


import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiagnosisService {

    DiagnosisResource findById(Long diagnosticId);
    List<DiagnosisResource> findAllDiagnostics();

    List<DiagnosisResource> findAllByPatientName(String patientName);
    List<DiagnosisResource> findAllByNurseFullname(String nurseName);
    List<DiagnosisResource> findAllByMedicFullname(String medicName);
    List<DiagnosisResource> findAllByStagePredicted(String stagePredicted);
    List<DiagnosisResource> findAllByNurseCEP(String nurseCEP);
    List<DiagnosisResource> findAllByMedicCMP(String medicCMP);
    /*List<DiagnosisResource> findAllByCreatorIdAndCreatorTypeAndStagePredicted(Long creatorId, Type creatorType, String stagePredicted);

    List<DiagnosisResource> findAllByCreatorIdAndCreatorType(Long creatorId, Type creatorType);*/

    DiagnosisResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws IOException;
    String confirmDiagnosisNurse(Long diagnosticId);
    String confirmDiagnosisMedic(Long diagnosticId);
    String deleteDiagnosisById(Long diagnosticId) ;
}
