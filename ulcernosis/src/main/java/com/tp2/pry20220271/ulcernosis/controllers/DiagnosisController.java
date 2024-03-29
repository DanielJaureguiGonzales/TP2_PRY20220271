package com.tp2.pry20220271.ulcernosis.controllers;


import com.tp2.pry20220271.ulcernosis.models.services.DiagnosisService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {


    private final DiagnosisService diagnosisService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{diagnosisId}")
    DiagnosisResource getDiagnosis(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.findById(diagnosisId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-patient-name/{patientName}")
    List<DiagnosisResource> getAllDiagnosticsByPatientName(@PathVariable("patientName") String patientName){
        return diagnosisService.findAllByPatientName(patientName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<DiagnosisResource> getAllDiagnostics(){
        return diagnosisService.findAllDiagnostics();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-nurse-name/{nurseName}")
    List<DiagnosisResource> getAllDiagnosticsByNurseName(@PathVariable("nurseName") String nurseName){
        return diagnosisService.findAllByNurseFullname(nurseName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-nurse-cep/{nurseCEP}")
    List<DiagnosisResource> getAllDiagnosticsByNurseCep(@PathVariable("nurseCEP") String nurseCEP){
        return diagnosisService.findAllByNurseCEP(nurseCEP);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-medic-cmp/{medicCMP}")
    List<DiagnosisResource> getAllDiagnosticsByMedicCmp(@PathVariable("medicCMP") String medicCMP){
        return diagnosisService.findAllByMedicCMP(medicCMP);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-medic-name/{medicName}")
    List<DiagnosisResource> getAllDiagnosticsByMedicName(@PathVariable("medicName") String medicName){
        return diagnosisService.findAllByMedicFullname(medicName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/medic-cmp/{medicCMP}/stage-predicted/{stagePredicted}")
    List<DiagnosisResource> getAllDiagnosticsByStagePredictedAndMedicCMP(@PathVariable("medicCMP") String medicCMP,@PathVariable("stagePredicted") String stagePredicted){
        return diagnosisService.findAllByStagePredictedMedic(medicCMP,stagePredicted);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurse-cep/{nurseCEP}/stage-predicted/{stagePredicted}")
    List<DiagnosisResource> getAllDiagnosticsByStagePredictedAndNurseCEP(@PathVariable("nurseCEP") String nurseCEP,@PathVariable("stagePredicted") String stagePredicted){
        return diagnosisService.findAllByStagePredictedNurse(nurseCEP,stagePredicted);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-diagnosis")
    DiagnosisResource createDiagnosis(@Valid SaveDiagnosisResource saveDiagnosisResource, @RequestParam("file") MultipartFile file) throws IOException {
        return diagnosisService.saveDiagnosis(saveDiagnosisResource, file);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/quick-diagnosis")
    DiagResource fastReferenceDiagnosis(@RequestParam("file") MultipartFile file) throws IOException {
        return diagnosisService.getDiagResourceCNN(file);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/confirm-diagnosis-nurse/{diagnosisId}")
    String confirmDiagnosisNurse(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.confirmDiagnosisNurse(diagnosisId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/confirm-diagnosis-medic/{diagnosisId}")
    String confirmDiagnosisMedic(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.confirmDiagnosisMedic(diagnosisId);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{diagnosisId}/delete-diagnosis")
    String deleteDiagnosis(@PathVariable("diagnosisId") Long diagnosisId){
        return diagnosisService.deleteDiagnosisById(diagnosisId);
    }

}
