package com.tp2.pry20220271.ulcernosis.auth;

import com.tp2.pry20220271.ulcernosis.config.JwtService;
import com.tp2.pry20220271.ulcernosis.exceptions.*;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Token;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import com.tp2.pry20220271.ulcernosis.models.enums.TokenType;
import com.tp2.pry20220271.ulcernosis.models.repositories.*;
import com.tp2.pry20220271.ulcernosis.models.services.MedicService;
import com.tp2.pry20220271.ulcernosis.models.services.NurseService;
import com.tp2.pry20220271.ulcernosis.resources.etc.*;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final UserRepository repository;
    private final PatientRepository patientRepository;

    private final MedicService medicService;


    private final MedicRepository medicRepository;


    private final NurseRepository nurseRepository;
    private final TokenRepository tokenRepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final NurseService nurseService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        if(patientRepository.findByDni(request.getDni()).isPresent() ||
                repository.findByDni(request.getDni()).isPresent())
        {
            throw new DniExistsException("El DNI ya está asociado a otra cuenta");

        }else if(patientRepository.findByEmail(request.getEmail()).isPresent() ||
                repository.findByEmail(request.getEmail()).isPresent())
        {

            throw new EmailExistsException("El email ya está asociado a otra cuenta");

        }else if(patientRepository.findByPhone(request.getPhone()).isPresent() ||
                repository.findByPhone(request.getPhone()).isPresent())
        {

            throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");

        }



        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dni(request.getDni())
                .age(request.getAge())
                .address(request.getAddress())
                .phone(request.getPhone())
                .role(request.getRole())
                .civilStatus(request.getCivilStatus())
                .avatar(new byte[]{})
                .build();

        var savedUser = repository.save(user);
        if (request.getRole() == Role.ROLE_MEDIC) {
            Medic medic = medicService.findMedicByCMP(request.getCmp());

            if (!(Objects.isNull(medic))){
                throw new CmpExistsException("El cmp ya está asociado a otro médico");
            }

           medicService.saveMedic(modelMapper.map(request, SaveMedicResource.class));


        } else if (request.getRole() == Role.ROLE_NURSE) {
            Nurse nurse = nurseService.findNurseByCEP(request.getCep());

            if (!(Objects.isNull(nurse))){
                throw new CepExistsException("El cep ya está asociado a otro enfermero");
            }

            nurseService.saveNurse(modelMapper.map(request, SaveNurseResource.class));

        }
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
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
        revokedAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseId authenticateId(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        /*var jwtToken = jwtService.generateToken(user);*/
        Long id = null;
        if (user.getRole() == Role.ROLE_MEDIC){
            id = medicRepository.findMedicByEmail(user.getEmail()).orElseThrow(()-> new NotFoundException("Medic","Email",user.getEmail())).getId();
        } else if (user.getRole() == Role.ROLE_NURSE ) {
            id = nurseRepository.findNurseByEmail(user.getEmail()).orElseThrow(()-> new NotFoundException("Nurse","Email",user.getEmail())).getId();
        }
       /* revokedAllUserTokens(user);
        saveUserToken(user, jwtToken);*/
        return AuthenticationResponseId.builder()
                .id(id)
                .type(user.getRole())
                .build();
    }
    public AuthenticateResponseUserId authenticateUserId(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        /*var jwtToken = jwtService.generateToken(user);
        revokedAllUserTokens(user);
        saveUserToken(user, jwtToken);*/
        return AuthenticateResponseUserId.builder()
                .id(user.getId())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token= Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokedAllUserTokens(User user){
        var validUserToken= tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(token -> {
           token.setExpired(true);
           token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }
}
