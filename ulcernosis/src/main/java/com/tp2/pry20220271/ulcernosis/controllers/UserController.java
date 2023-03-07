package com.tp2.pry20220271.ulcernosis.controllers;

import com.tp2.pry20220271.ulcernosis.models.services.UserService;
import com.tp2.pry20220271.ulcernosis.resources.response.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserResource> getAllUsers() {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findAll());
        return service.findAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    UserResource getUserById(@PathVariable("id") Long id) {
        //return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",medicService.findMedicById(medicId));
        return service.findById(id);
    }

}
