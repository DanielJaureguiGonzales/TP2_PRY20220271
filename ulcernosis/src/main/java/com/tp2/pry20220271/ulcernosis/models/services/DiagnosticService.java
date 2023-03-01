package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosticResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiagnosticService {

    List<DiagnosticResource> findAllByPatientId(Long patientId);

    List<DiagnosticResource> findAllByCreatorType(String creatorType);

    List<DiagnosticResource> findAllByStage1();


    List<DiagnosticResource> findAllByStage2();

    List<DiagnosticResource> findAllByStage3();

    List<DiagnosticResource> findAllByStage4();

    DiagnosticResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws UlcernosisException,IOException;

    String deleteDiagnosisById(Long diagnosticId) throws UlcernosisException;
}
