package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;

import com.tp2.pry20220271.ulcernosis.models.services.NurseService;

import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;

import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import com.tp2.pry20220271.ulcernosis.response.UlcernosisResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping("/nurse-controller")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurses")
    List<NurseResource> getAllNurses() throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",nurseService.findAll());
        return nurseService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurses/medic/{medicId}")
    List<NurseResource> getAllNursesByMedicId(@PathVariable("medicId") Long medicId) throws UlcernosisException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",nurseService.findAllByMedicId(medicId));
        return nurseService.findAllByMedicId(medicId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurses/{nurseId}")
    NurseResource getNurseById(@PathVariable("nurseId") Long nurseId) throws UlcernosisException{
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",nurseService.findNurseById(nurseId));
        return nurseService.findNurseById(nurseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nurses/{nurseId}/profile-photo")
    ResponseEntity<?> getNursePhoto(@PathVariable("nurseId") Long nurseId) throws UlcernosisException{
        Resource profile_photo = nurseService.findNursePhoto(nurseId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/nurses/{nurseId}/profile-photo")
    ResponseEntity<?> putNursePhoto(@PathVariable("nurseId") Long nurseId, @RequestParam("file") MultipartFile file) throws  IOException {
        Resource profile_photo = nurseService.putNursePhoto(nurseId, file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profile_photo);
    }

    /*
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/nurses/create-nurse")
    NurseResource createNurse(@Valid SaveNurseResource saveNurse) throws UlcernosisException, IOException {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.CREATED),"CREATED",nurseService.saveNurse(saveNurse));
        return nurseService.saveNurse(saveNurse);
    }
*/
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/nurses/{nurseId}/update-nurse")
    NurseResource updateNurse(@Valid SaveNurseResource updateNurse, @PathVariable("nurseId") Long nurseId) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"UPDATED",nurseService.updateNurse(nurseId,updateNurse));
        return nurseService.updateNurse(nurseId,updateNurse);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/nurses/{nurseId}/delete-nurse")
    String deleteNurse(@PathVariable("nurseId") Long nurseId){
        //return new UlcernosisResponse<>("Success", String.valueOf(HttpStatus.OK),"OK", nurseService.deleteNurse(nurseId));
        return nurseService.deleteNurse(nurseId);
    }

}
