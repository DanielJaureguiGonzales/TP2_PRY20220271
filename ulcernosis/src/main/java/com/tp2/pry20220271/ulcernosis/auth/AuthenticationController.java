package com.tp2.pry20220271.ulcernosis.auth;

import com.tp2.pry20220271.ulcernosis.exceptions.Error;
import com.tp2.pry20220271.ulcernosis.exceptions.ErrorExcepcion;
import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationRequest;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationResponse;
import com.tp2.pry20220271.ulcernosis.resources.etc.RegisterRequest;
import com.tp2.pry20220271.ulcernosis.response.UlcernosisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public UlcernosisResponse<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws ErrorExcepcion {

        return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",service.register(request));
    }

    @PostMapping("/authenticate")
    public UlcernosisResponse<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return new UlcernosisResponse<>("Success",String.valueOf(HttpStatus.OK),"OK",service.authenticate(request));
    }

}
