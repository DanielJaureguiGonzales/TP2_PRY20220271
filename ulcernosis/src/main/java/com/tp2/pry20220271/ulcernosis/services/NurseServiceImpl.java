package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.*;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.models.services.NurseService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;

import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NurseServiceImpl implements NurseService {

    private static final Logger log = LoggerFactory.getLogger(MedicServiceImpl.class);
    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private MedicRepository medicRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamWorkRepository teamWorkRepository;

    public NurseServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<NurseResource> findAll() {
        List<Nurse> nurses = nurseRepository.findAll();
        return nurses.stream().map(m -> mapper.map(m,NurseResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<NurseResource> findAllByMedicId(Long medicalId)  {
        Medic medic = medicRepository.findById(medicalId).orElseThrow(()-> new NotFoundException("Medic","id",medicalId.toString()));
        List<TeamWork> teamWorks = teamWorkRepository.findAllByMedicId(medicalId);
        List<Nurse> nurseList = nurseRepository.findAllByTeamWorkIn(teamWorks);
        return nurseList.stream().map(nurse -> mapper.map(nurse,NurseResource.class)).collect(Collectors.toList());
    }


    @Override
    public NurseResource findNurseById(Long id){
        Nurse nurse = nurseRepository.findById(id).orElseThrow(()-> new NotFoundException("Nurse","id",id));
        return mapper.map(nurse, NurseResource.class);
    }

    @Override
    public Nurse findNurseByCEP(String cep) {
        Nurse foundNurse = nurseRepository.findNurseByCep(cep).orElse(null);
        return foundNurse;
    }

    @Override
    public Resource findNursePhoto(Long id)  {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(()-> new NotFoundException("Nurse","id",id.toString()));
        Resource avatar = new ByteArrayResource(nurse.getAvatar());
        return avatar;
    }

    @Override
    public Resource putNursePhoto(Long id, MultipartFile file) throws IOException {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(()-> new NotFoundException("Nurse","id",id.toString()));
        User user = userRepository.findByEmail(nurse.getEmail()).orElseThrow(()-> new NotFoundException("User","email",nurse.getEmail()));

        nurse.setAvatar(file.getBytes());
        user.setAvatar(file.getBytes());
        nurseRepository.save(nurse);
        userRepository.save(user);

        Resource avatar = new ByteArrayResource(nurse.getAvatar());
        return avatar;

    }

    @Override
    public NurseResource saveNurse(SaveNurseResource saveNurseResource)  {


        Nurse newNurse = mapper.map(saveNurseResource,Nurse.class);
        newNurse.setRole(Rol.ROLE_NURSE);
        newNurse.setDni(saveNurseResource.getDni());
        newNurse.setAvatar(new byte[]{});
        newNurse.setPassword(passwordEncoder.encode(saveNurseResource.getPassword()));

        NurseResource nurseResource = mapper.map(nurseRepository.save(newNurse), NurseResource.class);
        nurseResource.setRole(Rol.ROLE_NURSE);
        return nurseResource;
    }

   @Override
    public NurseResource updateNurse(Long id, SaveNurseResource saveNurseResource) {
        Nurse updateNurse = getNurseByID(id);
        User updateUser = userRepository.findByEmail(updateNurse.getEmail()).orElseThrow(()-> new NotFoundException("User","email",updateNurse.getEmail()));
        updateNurse.setDni(saveNurseResource.getDni());
        updateNurse.setAge(saveNurseResource.getAge());
        updateNurse.setEmail(saveNurseResource.getEmail());
        updateNurse.setCivilStatus(saveNurseResource.getCivilStatus());
        updateNurse.setFullName(saveNurseResource.getFullName());
        updateNurse.setCep(saveNurseResource.getCep());
        updateNurse.setAddress(saveNurseResource.getAddress());
        updateNurse.setPassword(passwordEncoder.encode(saveNurseResource.getPassword()));
        updateNurse.setPhone(saveNurseResource.getPhone());
        updateNurse.setCivilStatus(saveNurseResource.getCivilStatus());

        updateUser.setEmail(saveNurseResource.getEmail());
        updateUser.setFullName(saveNurseResource.getFullName());
        updateUser.setCivilStatus(saveNurseResource.getCivilStatus());
        updateUser.setAddress(saveNurseResource.getAddress());
        updateUser.setDni(saveNurseResource.getDni());
        updateUser.setAge(saveNurseResource.getAge());
        updateUser.setPassword(passwordEncoder.encode(saveNurseResource.getPassword()));
        updateUser.setPhone(saveNurseResource.getPhone());
       userRepository.save(updateUser);

        return mapper.map(nurseRepository.save(updateNurse), NurseResource.class);
    }

    @Override
    public String deleteNurse(Long id) {
        Nurse deleteNurse = getNurseByID(id);
        User user = userRepository.findByEmail(deleteNurse.getEmail()).orElseThrow(()-> new NotFoundException("User","email",deleteNurse.getEmail()));
        userRepository.delete(user);
        nurseRepository.delete(deleteNurse);

        return "Se eliminó al enfermero exitosamente";
    }

    public Nurse getNurseByID(Long id)  {
        return nurseRepository.findById(id).orElseThrow(()->
                new NotFoundException("Nurse","Id", id)
        );
    }
}
