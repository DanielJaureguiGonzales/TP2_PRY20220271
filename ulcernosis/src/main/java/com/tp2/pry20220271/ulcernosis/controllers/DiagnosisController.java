package com.tp2.pry20220271.ulcernosis.controllers;


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
    @GetMapping("/diagnosis/{diagnosisId}")
    DiagnosisResource getDiagnosis(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.findById(diagnosisId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-patient-name/{patientName}")
    List<DiagnosisResource> getAllDiagnosticsByPatientName(@PathVariable("patientName") String patientName){
        return diagnosisService.findAllByPatientName(patientName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-nurse-name/{nurseName}")
    List<DiagnosisResource> getAllDiagnosticsByNurseName(@PathVariable("nurseName") String nurseName){
        return diagnosisService.findAllByNurseFullname(nurseName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-medic-name/{medicName}")
    List<DiagnosisResource> getAllDiagnosticsByMedicName(@PathVariable("medicName") String medicName){
        return diagnosisService.findAllByMedicFullname(medicName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stage-predicted/{stagePredicted}")
    List<DiagnosisResource> getAllDiagnosticsByStagePredicted(@PathVariable("stagePredicted") String stagePredicted){
        return diagnosisService.findAllByStagePredicted(stagePredicted);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create-diagnosis")
    DiagnosisResource createDiagnosis(@Valid SaveDiagnosisResource saveDiagnosisResource, @RequestParam("file") MultipartFile file) throws IOException {
        return diagnosisService.saveDiagnosis(saveDiagnosisResource, file);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete-diagnosis/{diagnosisId}")
    String deleteDiagnosis(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.deleteDiagnosisById(diagnosisId);
    }

}
