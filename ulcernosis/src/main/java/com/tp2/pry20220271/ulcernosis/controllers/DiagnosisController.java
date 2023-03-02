package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.services.DiagnosisService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-patient/{patientId}")
    List<DiagnosisResource> getAllDiagnosticsByPatientId(@PathVariable("patientId") Long patientId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findAll());
        return diagnosisService.findAllByPatientId(patientId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create-diagnosis")
    DiagnosisResource createDiagnosis(@Valid SaveDiagnosisResource saveDiagnosisResource, @RequestParam("file") MultipartFile file) throws UlcernosisException, IOException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",medicService.saveMedic(saveMedic));
        return diagnosisService.saveDiagnosis(saveDiagnosisResource, file);
    }

}
