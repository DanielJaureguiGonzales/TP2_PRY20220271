package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.services.DiagnosticService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosticResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ulcernosis")
public class DiagnosticController {

    @Autowired
    private DiagnosticService diagnosticService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/diagnosis/patient/{patientId}")
    List<DiagnosticResource> getAllDiagnosticsByPatientId(@PathVariable("patientId") Long patientId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findAll());
        return diagnosticService.findAllByPatientId(patientId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/diagnosis/create-patient")
    DiagnosticResource createMedic(@Valid SaveDiagnosisResource saveDiagnosisResource, @RequestParam("file") MultipartFile file) throws UlcernosisException, IOException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",medicService.saveMedic(saveMedic));
        return diagnosticService.saveDiagnosis(saveDiagnosisResource, file);
    }

}
