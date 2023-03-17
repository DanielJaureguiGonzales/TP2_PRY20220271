package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.DniExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.PhoneExistsException;
import com.tp2.pry20220271.ulcernosis.models.entities.*;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import com.tp2.pry20220271.ulcernosis.models.repositories.*;
import com.tp2.pry20220271.ulcernosis.models.services.NurseService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateNurseResource;
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
public class NurseServiceImpl implements NurseService {

    private static final ModelMapper mapper = new ModelMapper();


    private final NurseRepository nurseRepository;


    private final MedicRepository medicRepository;

    private final PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;


    private final TeamWorkRepository teamWorkRepository;

    private final AssignmentRepository assignmentRepository;
    private final PatientRepository patientRepository;

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
    public List<NurseResource> findAllByHaveTeamWork(Boolean isHaveTeamWork) {
        List<Nurse> nurseList = nurseRepository.findAllByHaveTeamWork(isHaveTeamWork);
        return nurseList.stream().map(nurse -> mapper.map(nurse,NurseResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<NurseResource> findAllByIsAuxiliar(Boolean isAuxiliar) {
        List<Nurse> nurseList = nurseRepository.findAllByIsAuxiliar(isAuxiliar);
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
        newNurse.setHaveTeamWork(false);
        NurseResource nurseResource = mapper.map(nurseRepository.save(newNurse), NurseResource.class);
        nurseResource.setRole(Rol.ROLE_NURSE);
        return nurseResource;
    }

   @Override
    public NurseResource updateNurse(Long id, UpdateNurseResource updateNurseResource) {
        Nurse updateNurse = getNurseByID(id);
        User updateUser = userRepository.findByEmail(updateNurse.getEmail()).orElseThrow(()-> new NotFoundException("User","email",updateNurse.getEmail()));

       if (!updateNurse.getDni().equals(updateNurseResource.getDni())){
           if(patientRepository.findByDni(updateNurseResource.getDni()).isPresent() ||
                   userRepository.findByDni(updateNurseResource.getDni()).isPresent()){
               throw new DniExistsException("El DNI ya está asociado a otra cuenta");
           }
       }

      /*if (!updateNurse.getEmail().equals(updateNurseResource.getEmail())){
          if(patientRepository.findByEmail(updateNurseResource.getEmail()).isPresent() ||
                  userRepository.findByEmail(updateNurseResource.getEmail()).isPresent()){
              throw new EmailExistsException("El email ya está asociado a otra cuenta");
          }
       }
*/
       if (!updateNurse.getPhone().equals(updateNurseResource.getPhone())){
           if(patientRepository.findByPhone(updateNurseResource.getPhone()).isPresent() ||
                   userRepository.findByPhone(updateNurseResource.getPhone()).isPresent()){
               throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");
           }
       }



        updateNurse.setDni(updateNurseResource.getDni());
        updateNurse.setAge(updateNurseResource.getAge());
        /*updateNurse.setEmail(updateNurseResource.getEmail());*/
        updateNurse.setCivilStatus(updateNurseResource.getCivilStatus());
        updateNurse.setFullName(updateNurseResource.getFullName());
        updateNurse.setAddress(updateNurseResource.getAddress());
        updateNurse.setPhone(updateNurseResource.getPhone());
        updateNurse.setCivilStatus(updateNurseResource.getCivilStatus());

        /*updateUser.setEmail(updateNurseResource.getEmail());*/
        updateUser.setFullName(updateNurseResource.getFullName());
        updateUser.setCivilStatus(updateNurseResource.getCivilStatus());
        updateUser.setAddress(updateNurseResource.getAddress());
        updateUser.setDni(updateNurseResource.getDni());
        updateUser.setAge(updateNurseResource.getAge());
        updateUser.setPhone(updateNurseResource.getPhone());
        userRepository.save(updateUser);

        return mapper.map(nurseRepository.save(updateNurse), NurseResource.class);
    }

    @Override
    public String deleteNurse(Long id) {
        Nurse deleteNurse = getNurseByID(id);
        User user = userRepository.findByEmail(deleteNurse.getEmail()).orElseThrow(()-> new NotFoundException("User","email",deleteNurse.getEmail()));
        List<Assignment> assignments = assignmentRepository.findAllByNurseId(id);
        // Encontramos todos los pacientes que tiene asignado el enfermero
        List<Patient> patients = assignments.stream().map(assignment -> assignment.getPatient()).collect(Collectors.toList());
        // Cambiamos el estado de asignado de un enfermero con el paciente a false
        for (Patient patient:patients) {
            patient.setIsAssigned(false);
        }

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
