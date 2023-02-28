package com.tp2.pry20220271.ulcernosis.auth;


import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationRequest;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationResponse;
import com.tp2.pry20220271.ulcernosis.resources.etc.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request) throws UlcernosisException {
        return service.register(request);

    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);

    }

}
