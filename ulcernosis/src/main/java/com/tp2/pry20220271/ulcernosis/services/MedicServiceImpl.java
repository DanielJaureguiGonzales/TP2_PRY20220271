package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.models.services.MedicService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
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
public class MedicServiceImpl implements MedicService {


    private static final Logger log = LoggerFactory.getLogger(MedicServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private UserRepository userRepository;

    private static final ModelMapper mapper = new ModelMapper();

    public MedicServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<MedicResource> findAll()  {
        List<Medic> medicos = medicRepository.findAll();
        return medicos.stream().map(
                medic -> mapper.map(medic,MedicResource.class)
        ).collect(Collectors.toList());
    }

    @Override
    public MedicResource findMedicById(Long id) {
       return mapper.map(getMedicByID(id), MedicResource.class);
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

        newMedic.setAvatar(new byte[]{});
        newMedic.setDni(saveMedicResource.getDni());
        newMedic.setPassword(passwordEncoder.encode(saveMedicResource.getPassword()));

        Medic saveMedic=medicRepository.save(newMedic);

        return mapper.map(saveMedic, MedicResource.class);
    }

    @Override
    public MedicResource updateMedic(Long id, SaveMedicResource saveMedicResource) {
       Medic updateMedic = getMedicByID(id);
       User updateUser = userRepository.findByEmail(updateMedic.getEmail()).orElseThrow(()-> new NotFoundException("User","email",updateMedic.getEmail()));

        updateMedic.setDni(saveMedicResource.getDni());
        updateMedic.setAge(Integer.valueOf(saveMedicResource.getAge()));
        updateMedic.setEmail(saveMedicResource.getEmail());
        updateMedic.setFullName(saveMedicResource.getFullName());
        updateMedic.setCmp(saveMedicResource.getCmp());
        updateMedic.setCivilStatus(saveMedicResource.getCivilStatus());
        updateMedic.setAddress(saveMedicResource.getAddress());
        updateMedic.setPassword(passwordEncoder.encode(saveMedicResource.getPassword()));
        updateMedic.setPhone(saveMedicResource.getPhone());

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
