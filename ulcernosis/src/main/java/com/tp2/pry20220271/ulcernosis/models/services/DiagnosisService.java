package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiagnosisService {

    List<DiagnosisResource> findAllByPatientId(Long patientId);

    List<DiagnosisResource> findAllByCreatorType(String creatorType);

    List<DiagnosisResource> findAllByStage1();


    List<DiagnosisResource> findAllByStage2();

    List<DiagnosisResource> findAllByStage3();

    List<DiagnosisResource> findAllByStage4();

    DiagnosisResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws UlcernosisException,IOException;

    String deleteDiagnosisById(Long diagnosticId) throws UlcernosisException;
}
