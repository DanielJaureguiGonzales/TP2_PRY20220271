package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.models.services.PatientService;
import com.tp2.pry20220271.ulcernosis.resources.request.SavePatientResource;
import com.tp2.pry20220271.ulcernosis.resources.response.PatientResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {


    private final PatientService patientService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_by_medic/{medicId}")
    List<PatientResource> getAllPatientsByMedicId(@PathVariable("medicId") Long medicId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",patientService.findAllPatientsByMedicId(medicId));
        return patientService.findAllPatientsByMedicId(medicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/medic/{medicId}/get-patients-by-assigned/{isAssigned}")
    List<PatientResource> getAllPatientsByAssignedBool(@PathVariable("medicId") Long medicId,@PathVariable("isAssigned") Boolean isAssigned) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",patientService.findAllPatientsByMedicId(medicId));
        return patientService.findAllPatientsByMedicIdAndIsAssigned(medicId,isAssigned);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{patientId}")
    PatientResource getPatientById(@PathVariable("patientId") Long patientId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",patientService.findPatientById(patientId));
        return patientService.findPatientById(patientId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all-by-nurse/{nurseId}")
    List<PatientResource> getAllPatientsByNurseId(@PathVariable("nurseId") Long nurseId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",patientService.findAllByNurseId(nurseId));
        return patientService.findAllPatientsByNurseIdAndIsAssigned(nurseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{patientId}/profile-photo")
    ResponseEntity<?> getPatientPhoto(@PathVariable("patientId") Long patientId){

        Resource profile_photo = patientService.findPatientPhoto(patientId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{patientId}/profile-photo")
    ResponseEntity<?> putPatientPhoto(@PathVariable("patientId") Long patientId, @RequestParam("file") MultipartFile file) throws IOException {
        Resource profile_photo = patientService.putPatientPhoto(patientId, file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/medic/{medicId}/create-patient")
    PatientResource createPatient(@Valid @RequestBody SavePatientResource savePatient, @PathVariable("medicId") Long medicId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",patientService.createPatient(savePatient));
        return patientService.createPatient(savePatient,medicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{patientId}/update-patient")
    PatientResource updatePatient(@Valid @RequestBody SavePatientResource updatedPatient, @PathVariable("patientId") Long patientId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"UPDATED",patientService.updatePatient(updatedPatient,patientId));
        return patientService.updatePatient(updatedPatient,patientId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{patientId}/delete-patient")
    String deleteNurse(@PathVariable("patientId") Long patientId){
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", patientService.deletePatient(patientId));
        return patientService.deletePatient(patientId);
    }

}
