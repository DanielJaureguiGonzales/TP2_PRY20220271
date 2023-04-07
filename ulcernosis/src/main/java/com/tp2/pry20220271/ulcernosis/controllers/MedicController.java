package com.tp2.pry20220271.ulcernosis.controllers;


import com.tp2.pry20220271.ulcernosis.models.services.MedicService;

import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;

import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateMedicResource;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medics")
public class MedicController {


    private final MedicService medicService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<MedicResource> getAllMedics() {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findAll());
        return medicService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{medicId}")
    MedicResource getMedicById(@PathVariable("medicId") Long medicId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findMedicById(medicId));
        return medicService.findMedicById(medicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{medicId}/profile-photo")
    ResponseEntity<?> getMedicPhoto(@PathVariable("medicId") Long medicId) {
        Resource profile_photo = medicService.findMedicPhoto(medicId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{medicId}/profile-photo")
    ResponseEntity<?> putMedicPhoto(@PathVariable("medicId") Long medicId, @RequestParam("file") MultipartFile file) throws IOException {
        Resource profile_photo = medicService.updateMedicPhoto(medicId, file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }


  /*  @ResponseStatus(HttpStatus.OK)
    @PostMapping("/medics/create-medic")
    MedicResource createMedic(@Valid SaveMedicResource saveMedic) throws UlcernosisException, IOException{
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",medicService.saveMedic(saveMedic));
        return medicService.saveMedic(saveMedic);
    }*/

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{medicId}/update-medic")
    MedicResource updateMedic(@Valid @RequestBody UpdateMedicResource updateMedic, @PathVariable("medicId") Long medicId){
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"UPDATED",medicService.updateMedic(medicId,updateMedic));
        return medicService.updateMedic(medicId,updateMedic);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-medic-by-nurse/{nurseId}")
    MedicResource getMedicByNurseId(@PathVariable("nurseId") Long nurseId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findMedicById(medicId));
        return medicService.findMedicByNurseId(nurseId);
    }

   /* @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{medicId}/delete-medic")
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    String deleteMedic(@PathVariable("medicId") Long medicId){
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", medicService.deleteMedic(medicId));
        return medicService.deleteMedic(medicId);
    }*/


}
