package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.DniExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.EmailExistsException;
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
import lombok.RequiredArgsConstructor;
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
    public MedicResource updateMedic(Long id, SaveMedicResource saveMedicResource) {
       Medic updateMedic = getMedicByID(id);
       User updateUser = userRepository.findByEmail(updateMedic.getEmail()).orElseThrow(()-> new NotFoundException("User","email",updateMedic.getEmail()));


        if (!updateMedic.getDni().equals(saveMedicResource.getDni())){
            if(patientRepository.findByDni(saveMedicResource.getDni()).isPresent() ||
                    userRepository.findByDni(saveMedicResource.getDni()).isPresent()){
                throw new DniExistsException("El DNI ya está asociado a otra cuenta");
            }
        }

        if (!updateMedic.getEmail().equals(saveMedicResource.getEmail())){
            if(patientRepository.findByEmail(saveMedicResource.getEmail()).isPresent() ||
                    userRepository.findByEmail(saveMedicResource.getEmail()).isPresent()){
                throw new EmailExistsException("El email ya está asociado a otra cuenta");
            }
        }

        if (!updateMedic.getPhone().equals(saveMedicResource.getPhone())){
            if(patientRepository.findByPhone(saveMedicResource.getPhone()).isPresent() ||
                    userRepository.findByPhone(saveMedicResource.getPhone()).isPresent()){
                throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");
            }
        }


        updateMedic.setDni(saveMedicResource.getDni());
        updateMedic.setAge(Integer.valueOf(saveMedicResource.getAge()));
        updateMedic.setEmail(saveMedicResource.getEmail());
        updateMedic.setFullName(saveMedicResource.getFullName());
        updateMedic.setCmp(saveMedicResource.getCmp());
        updateMedic.setCivilStatus(saveMedicResource.getCivilStatus());
        updateMedic.setAddress(saveMedicResource.getAddress());
        updateMedic.setPassword(passwordEncoder.encode(saveMedicResource.getPassword()));
        updateMedic.setPhone(saveMedicResource.getPhone());
        updateMedic.setCivilStatus(saveMedicResource.getCivilStatus());

        updateUser.setEmail(saveMedicResource.getEmail());
        updateUser.setFullName(saveMedicResource.getFullName());
        updateUser.setCivilStatus(saveMedicResource.getCivilStatus());
        updateUser.setAddress(saveMedicResource.getAddress());
        updateUser.setDni(saveMedicResource.getDni());
        updateUser.setAge(Integer.valueOf(saveMedicResource.getAge()));
        updateUser.setPassword(passwordEncoder.encode(saveMedicResource.getPassword()));
        updateUser.setPhone(saveMedicResource.getPhone());
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
