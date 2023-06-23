package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.DniExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.EmailExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.PhoneExistsException;
import com.tp2.pry20220271.ulcernosis.models.entities.Appointment;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.enums.Status;
import com.tp2.pry20220271.ulcernosis.models.repositories.*;
import com.tp2.pry20220271.ulcernosis.models.services.PatientService;
import com.tp2.pry20220271.ulcernosis.resources.request.SavePatientResource;
import com.tp2.pry20220271.ulcernosis.resources.response.PatientResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {


    private static final ModelMapper mapper = new ModelMapper();


    private final PatientRepository patientRepository;


    private final MedicRepository medicRepository;


    private final NurseRepository nurseRepository;


    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<PatientResource> findAllPatientsByMedicId(Long medicId){
        final Medic searchMedic = medicRepository.findById(medicId).orElseThrow(()-> new NotFoundException("Medic","Id",medicId));
        List<Patient> patients_founds = patientRepository.findAllByMedicId(searchMedic.getId());
        return patients_founds.stream().map(patient_found-> mapper.map(patient_found,PatientResource.class)).collect(Collectors.toList());
    }



    @Override
    public PatientResource findPatientById(Long id) {
        return mapper.map(getPatientByID(id), PatientResource.class);
    }

    @Override
    public Resource findPatientPhoto(Long id) {
        Patient patient = getPatientByID(id);
        Resource avatar = new ByteArrayResource(patient.getAvatar());
        return avatar;
    }

    @Override
    public Resource putPatientPhoto(Long id, MultipartFile file) throws IOException {
        Patient patient = getPatientByID(id);
        patient.setAvatar(file.getBytes());
        patientRepository.save(patient);

        Resource avatar = new ByteArrayResource(patient.getAvatar());
        return avatar;
    }

    @Override
    public PatientResource createPatient(SavePatientResource savePatientResource, Long medicId) {
        final Medic assignMedic = medicRepository.findById(medicId).orElseThrow(()->
            new NotFoundException("Medic","Id",medicId)
        );



        if(patientRepository.findByDni(savePatientResource.getDni()).isPresent() ||
                userRepository.findByDni(savePatientResource.getDni()).isPresent()){
            throw new DniExistsException("El DNI ya está asociado a otra cuenta");
        }else if(patientRepository.findByEmail(savePatientResource.getEmail()).isPresent() ||
                userRepository.findByEmail(savePatientResource.getEmail()).isPresent()){
            throw new EmailExistsException("El email ya está asociado a otra cuenta");
        }else if(patientRepository.findByPhone(savePatientResource.getPhone()).isPresent() ||
                userRepository.findByPhone(savePatientResource.getPhone()).isPresent()){
            throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");
        }

        Patient newPatient = new Patient();
        newPatient.setFullName(savePatientResource.getFullName());
        newPatient.setAge(savePatientResource.getAge());
        newPatient.setEmail(savePatientResource.getEmail());
        newPatient.setAddress(savePatientResource.getAddress());
        newPatient.setDni(savePatientResource.getDni());
        newPatient.setAvatar(new byte[]{});
        newPatient.setCivilStatus(savePatientResource.getCivilStatus());
        newPatient.setMedic(assignMedic);
        newPatient.setPhone(savePatientResource.getPhone());
        newPatient.setIsAssigned(false);
        Patient savePatient = patientRepository.save(newPatient);
        return mapper.map(savePatient,PatientResource.class);
    }

    @Override
    public PatientResource updatePatient(SavePatientResource savePatientResource, Long patientId){

        Patient updatePatient = getPatientByID(patientId);
        if (!updatePatient.getDni().equals(savePatientResource.getDni())){
            if(patientRepository.findByDni(savePatientResource.getDni()).isPresent() ||
                    userRepository.findByDni(savePatientResource.getDni()).isPresent()){
                throw new DniExistsException("El DNI ya está asociado a otra cuenta");
            }
        }

        if (!updatePatient.getEmail().equals(savePatientResource.getEmail())){
            if(patientRepository.findByEmail(savePatientResource.getEmail()).isPresent() ||
                    userRepository.findByEmail(savePatientResource.getEmail()).isPresent()){
                throw new EmailExistsException("El email ya está asociado a otra cuenta");
            }
        }

        if (!updatePatient.getPhone().equals(savePatientResource.getPhone())){
            if(patientRepository.findByPhone(savePatientResource.getPhone()).isPresent() ||
                    userRepository.findByPhone(savePatientResource.getPhone()).isPresent()){
                throw new PhoneExistsException("El teléfono ya está asociado a otra cuenta");
            }
        }


        updatePatient.setFullName(savePatientResource.getFullName());
        updatePatient.setAge(savePatientResource.getAge());
        updatePatient.setEmail(savePatientResource.getEmail());
        updatePatient.setAddress(savePatientResource.getAddress());
        updatePatient.setDni(savePatientResource.getDni());
        updatePatient.setCivilStatus(savePatientResource.getCivilStatus());
        updatePatient.setPhone(savePatientResource.getPhone());

        return  mapper.map(patientRepository.save(updatePatient),PatientResource.class);
    }

    @Override
    public String deletePatient(Long patientId){
        Patient patient = getPatientByID(patientId);
        patientRepository.delete(patient);

        return "Se eliminó al paciente exitosamente";
    }

    /*@Override
    public List<PatientResource> findAllByAssigned(Long medicId, Boolean isAssigned) {
        List<Patient> patients = patientRepository.findAllByIsAssigned(isAssigned);
        return patients.stream().map(patient -> mapper.map(patient,PatientResource.class)).collect(Collectors.toList());
    }*/

    /*@Override
    public List<PatientResource> findAllByAssigned(Boolean isAssigned) {
        List<Patient> patients = patientRepository.findAllByIsAssigned(isAssigned);
        return patients.stream().map(patient -> mapper.map(patient,PatientResource.class)).collect(Collectors.toList());
    }*/

    @Override
    public List<PatientResource> findAllPatientsByMedicIdAndIsAssigned(Long medicId, Boolean isAssigned) {
        final Medic searchMedic = medicRepository.findById(medicId).orElseThrow(()-> new NotFoundException("Medic","Id",medicId));
        List<Patient> patients_founds = patientRepository.findAllByMedicIdAndIsAssigned(searchMedic.getId(),isAssigned);
        return patients_founds.stream().map(patient_found-> mapper.map(patient_found,PatientResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<PatientResource> findAllPatientsByNurseIdAndIsAssigned(Long nurseId){
        Nurse searchNurse = nurseRepository.findById(nurseId).orElseThrow(()-> new NotFoundException("Nurse","Id",nurseId));
        List<Appointment> appointments = appointmentRepository.findAllByNurseId(searchNurse.getId()).stream().filter((a)-> a.getStatus()== Status.PENDIENTE).toList();
        List<Long> patients_ids_founds = new ArrayList<>();
        for (Appointment appointment : appointments
             ) {
            patients_ids_founds.add(appointment.getPatientId());
        }
        List<Patient> patients_founds = patientRepository.findAllByIdIn(patients_ids_founds).stream().filter(Patient::getIsAssigned).toList();
        return patients_founds.stream().map((e)-> mapper.map(e,PatientResource.class)).collect(Collectors.toList());
    }

    public Patient getPatientByID(Long id){
        return patientRepository.findById(id).orElseThrow(()->
                new NotFoundException("Patient","Id",id)
        );
    }
}
