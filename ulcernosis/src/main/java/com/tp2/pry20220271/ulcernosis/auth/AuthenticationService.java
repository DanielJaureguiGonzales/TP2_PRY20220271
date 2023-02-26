package com.tp2.pry20220271.ulcernosis.auth;

import com.tp2.pry20220271.ulcernosis.config.JwtService;
import com.tp2.pry20220271.ulcernosis.exceptions.Error;
import com.tp2.pry20220271.ulcernosis.exceptions.ErrorExcepcion;
import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationRequest;
import com.tp2.pry20220271.ulcernosis.resources.etc.AuthenticationResponse;
import com.tp2.pry20220271.ulcernosis.resources.etc.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    @Autowired
    private MedicRepository medicRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    @Autowired
    private NurseRepository nurseRepository;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws ErrorExcepcion {

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dni(request.getDni())
                .age(request.getAge())
                .address(request.getAddress())
                .role(request.getRol())
                .civilStatus(request.getCivilStatus())
                .avatar(new byte[]{})
                .build();
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new ErrorExcepcion(Error.INTERNAL_SERVER_ERROR);
        }
        if (request.getRol() == Rol.MEDIC) {
            var medic = Medic.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .dni(request.getDni())
                    .age(request.getAge())
                    .address(request.getAddress())
                    .avatar(new byte[]{})
                    .cmp(request.getCmp())
                    .civilStatus(request.getCivilStatus())
                    .build();
            try {
                medicRepository.save(medic);
            } catch (Exception e) {
                throw new ErrorExcepcion(Error.INTERNAL_SERVER_ERROR);
            }

        } else if (request.getRol() == Rol.NURSE) {
            var nurse = Nurse.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .dni(request.getDni())
                    .age(request.getAge())
                    .address(request.getAddress())
                    .avatar(new byte[]{})
                    .cep(request.getCep())
                    .civilStatus(request.getCivilStatus())
                    .build();
            try {
                nurseRepository.save(nurse);
            } catch (Exception e) {
                throw new ErrorExcepcion(Error.INTERNAL_SERVER_ERROR);
            }

        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
