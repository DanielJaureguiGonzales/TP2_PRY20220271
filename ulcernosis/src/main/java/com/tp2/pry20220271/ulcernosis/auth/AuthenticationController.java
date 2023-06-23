package com.tp2.pry20220271.ulcernosis.auth;


import com.tp2.pry20220271.ulcernosis.resources.etc.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request){
        return service.register(request);

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);

    }

    // ESTE DEVUELVE EL ID DEL MÉDICO O ENFERMERO
    @PostMapping("/get-type-id/authenticateId")
    public AuthenticationResponseId authenticateTypeByTypeId(@RequestBody AuthenticationRequest authenticateRequest) {
        return service.authenticateId(authenticateRequest);
    }


    // ESTE DEVUELVE EL ID DEL USUARIO
    @PostMapping("/get-user-id/authenticateId")
    public AuthenticateResponseUserId authenticateByUserId(@RequestBody AuthenticationRequest authenticateRequest) {
        return service.authenticateUserId(authenticateRequest);
    }
}
