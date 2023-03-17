package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.DniExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.PhoneExistsException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.models.services.MedicService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateMedicResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl implements MedicService {


    private final PasswordEncoder passwordEncoder;

    private final MedicRepository medicRepository;

    private final UserRepository userRepository;

    private final PatientRepository patientRepository;
    private final TeamWorkRepository teamWorkRepository;

    private static final ModelMapper mapper = new ModelMapper();



    @Override
    public List<MedicResource> findAll()  {
        List<Medic> medicos = medicRepository.findAll();
        return medicos.stream().map(
                medic -> mapper.map(medic,MedicResource.class)
        ).collect(Collectors.toList());
    }

    @Override
    public MedicResource findMedicById(Long id) {
        Medic medic = medicRepository.findById(id).orElseThrow(()-> new NotFoundException("Medic","id",id));
        return mapper.map(medic,MedicResource.class);

    }

    @Override
    public MedicResource findMedicByFullName(String fullname){
        Medic foundMedic = medicRepository.findMedicByFullName(fullname).orElseThrow(()-> new NotFoundException("User","fullname",fullname));
        return mapper.map(foundMedic,MedicResource.class);
    }

    @Override
    public Resource findMedicPhoto(Long id) {
        Medic medic = getMedicByID(id);
        Resource avatar = new ByteArrayResource(medic.getAvatar());
        return avatar;
    }

    @Override
    public Resource updateMedicPhoto(Long id, MultipartFile avatar) throws IOException {
        Medic medic = getMedicByID(id);
        User user = userRepository.findByEmail(medic.getEmail()).orElseThrow(()-> new NotFoundException("User","email",medic.getEmail()));


        medic.setAvatar(avatar.getBytes());
        user.setAvatar(avatar.getBytes());
        medicRepository.save(medic);
        userRepository.save(user);
        Resource avatar1 = new ByteArrayResource(medic.getAvatar());
        return avatar1;
    }

    @Override
    public MedicResource saveMedic(SaveMedicResource saveMedicResource) {

        Medic newMedic = mapper.map(saveMedicResource,Medic.class);
        newMedic.setRole(Rol.ROLE_MEDIC);
        newMedic.setAvatar(new byte[]{});
        newMedic.setDni(saveMedicResource.getDni());
        newMedic.setPassword(passwordEncoder.encode(saveMedicResource.getPassword()));
        MedicResource medicResource = mapper.map(medicRepository.save(newMedic),MedicResource.class);
        medicResource.setRole(Rol.ROLE_MEDIC);
        return medicResource;
    }

    @Override
    public MedicResource updateMedic(Long id, UpdateMedicResource updateMedicResource) {
       Medic updateMedic = getMedicByID(id);
       User updateUser = userRepository.findByEmail(updateMedic.getEmail()).orElseThrow(()-> new NotFoundException("User","email",updateMedic.getEmail()));


        if (!updateMedic.getDni().equals(updateMedicResource.getDni())){
            if(patientRepository.findByDni(updateMedicResource.getDni()).isPresent() ||
                    userRepository.findByDni(updateMedicResource.getDni()).isPresent()){
                throw new DniExistsException("El DNI ya está asociado a otra cuenta");
            }
        }

        /*if (!updateMedic.getEmail().equals(updateMedicResource.getEmail())){
            if(patientRepository.findByEmail(updateMedicResource.getEmail()).isPresent() ||
                    userRepository.findByEmail(updateMedicResource.getEmail()).isPresent()){
                throw new EmailExistsException("El email ya está asociado a otra cuenta");
            }
        }*/

        if (!updateMedic.getPhone().equals(updateMedicResource.getPhone())){
            if(patientRepository.findByPhone(updateMedicResource.getPhone()).isPresent() ||
                    userRepository.findByPhone(updateMedicResource.getPhone()).isPresent()){
                throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");
            }
        }


        updateMedic.setDni(updateMedicResource.getDni());
        updateMedic.setAge(Integer.valueOf(updateMedicResource.getAge()));
        /*updateMedic.setEmail(updateMedicResource.getEmail());*/
        updateMedic.setFullName(updateMedicResource.getFullName());
        updateMedic.setCivilStatus(updateMedicResource.getCivilStatus());
        updateMedic.setAddress(updateMedicResource.getAddress());
        updateMedic.setPhone(updateMedicResource.getPhone());
        updateMedic.setCivilStatus(updateMedicResource.getCivilStatus());

       /* updateUser.setEmail(updateMedicResource.getEmail());*/
        updateUser.setFullName(updateMedicResource.getFullName());
        updateUser.setCivilStatus(updateMedicResource.getCivilStatus());
        updateUser.setAddress(updateMedicResource.getAddress());
        updateUser.setDni(updateMedicResource.getDni());
        updateUser.setAge(Integer.valueOf(updateMedicResource.getAge()));
        updateUser.setPhone(updateMedicResource.getPhone());
        userRepository.save(updateUser);

       return  mapper.map(medicRepository.save(updateMedic), MedicResource.class);
    }

    @Override
    public Medic findMedicByCMP(String cmp) {
        Medic medic = medicRepository.findMedicByCmp(cmp).orElse(null);
        return medic;
    }

    @Override
    public String deleteMedic(Long id) {

        Medic deleteMedic = getMedicByID(id);
        User user = userRepository.findByEmail(deleteMedic.getEmail()).orElseThrow(()-> new NotFoundException("User","email",deleteMedic.getEmail()));
        List<TeamWork> teamWorks = teamWorkRepository.findAllByMedicId(deleteMedic.getId());
        // Encotrar todos los enfermeros que estan en el equipo de trabajo del medico
        List<Nurse> nurses = teamWorks.stream().map(TeamWork::getNurse).collect(Collectors.toList());
        // Eliminar el equipo de trabajo del medico
        for(Nurse nurse: nurses){
            nurse.setHaveTeamWork(false);
        }
        medicRepository.delete(deleteMedic);
        userRepository.delete(user);
        return "Se eliminó al médico exitosamente";
    }

    public Medic getMedicByID(Long id) {
        return medicRepository.findById(id).orElseThrow(()->
            new NotFoundException("User","Id",id)
        );
    }
}
