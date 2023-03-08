package com.tp2.pry20220271.ulcernosis.auth;

import com.tp2.pry20220271.ulcernosis.config.JwtService;
import com.tp2.pry20220271.ulcernosis.exceptions.*;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.models.services.MedicService;
import com.tp2.pry20220271.ulcernosis.models.services.NurseService;
import com.tp2.pry20220271.ulcernosis.resources.etc.*;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final UserRepository repository;

    private final MedicService medicService;


    private final MedicRepository medicRepository;


    private final NurseRepository nurseRepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final NurseService nurseService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> userEmailExists = repository.findByEmail(request.getEmail());
        Optional<User> userPhoneExists = repository.findByPhone(request.getPhone());
        Optional<User> userDniExists = repository.findByDni(request.getDni());
        /*Optional<Medic> medicByCmpExists = medicService.findMedicByCMP(request.getCmp());
        Optional<Nurse> nurseByCepExists = nurseService.findNurseByCEP(request.getCep());*/

        if (userEmailExists.isPresent()) {
            throw new EmailExistsException("El email ya está asociado con otra cuenta");
        } else if (userPhoneExists.isPresent()){
            throw new PhoneExistsException("El teléfono ya está asociado con otra cuenta");
        } else if (userDniExists.isPresent()) {
            throw new DniExistsException("El dni ya está asociado con otra cuenta");
        }


        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dni(request.getDni())
                .age(Integer.valueOf(request.getAge()))
                .address(request.getAddress())
                .phone(request.getPhone())
                .role(request.getRole())
                .civilStatus(request.getCivilStatus())
                .avatar(new byte[]{})
                .build();



        if (request.getRole() == Rol.ROLE_MEDIC) {
            Medic medic = medicService.findMedicByCMP(request.getCmp());

            if (!(Objects.isNull(medic))){
                throw new CmpExistsException("El cmp ya está asociado a otro médico");
            }

           repository.save(user);
           medicService.saveMedic(modelMapper.map(request, SaveMedicResource.class));


        } else if (request.getRole() == Rol.ROLE_NURSE) {
            Nurse nurse = nurseService.findNurseByCEP(request.getCep());

            if (!(Objects.isNull(nurse))){
                throw new CepExistsException("El cep ya está asociado a otro enfermero");
            }

            repository.save(user);
            nurseService.saveNurse(modelMapper.map(request, SaveNurseResource.class));

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

    public AuthenticationResponseId authenticateId(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        Long id = null;
        if (user.getRole() == Rol.ROLE_MEDIC){
            id = medicRepository.findMedicByEmail(user.getEmail()).orElseThrow(()-> new NotFoundException("Medic","Email",user.getEmail())).getId();
        } else if (user.getRole() == Rol.ROLE_NURSE ) {
            id = nurseRepository.findNurseByEmail(user.getEmail()).orElseThrow(()-> new NotFoundException("Nurse","Email",user.getEmail())).getId();
        }
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
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponseUserId.builder()
                .id(user.getId())
                .build();
    }


}
