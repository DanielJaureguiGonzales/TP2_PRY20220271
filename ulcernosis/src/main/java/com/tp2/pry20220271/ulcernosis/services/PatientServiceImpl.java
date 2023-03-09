package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.DniExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.EmailExistsException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.PhoneExistsException;
import com.tp2.pry20220271.ulcernosis.models.entities.Assignment;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.repositories.AssignmentRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.services.PatientService;
import com.tp2.pry20220271.ulcernosis.resources.request.SavePatientResource;
import com.tp2.pry20220271.ulcernosis.resources.response.PatientResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private static final ModelMapper mapper = new ModelMapper();


    private final PatientRepository patientRepository;


    private final MedicRepository medicRepository;


    private final NurseRepository nurseRepository;


    private final AssignmentRepository assignmentRepository;

    @Override
    public List<PatientResource> findAllPatientsByMedicId(Long medicId){
        final Medic searchMedic = medicRepository.findById(medicId).orElseThrow(()-> new NotFoundException("Medic","Id",medicId));
        List<Patient> patients_founds = patientRepository.findAllByMedicId(searchMedic.getId());
        return patients_founds.stream().map(patient_found-> mapper.map(patient_found,PatientResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<PatientResource> findAllByNurseId(Long nurseId){
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(()-> new NotFoundException("Nurse","Id",nurseId));
        List<Assignment> assignments = assignmentRepository.findAllByNurseId(nurse.getId());
        List<Patient> patients = patientRepository.findAllByAssignmentsIn(assignments);
        return patients.stream().map(patient -> mapper.map(patient,PatientResource.class)).collect(Collectors.toList());

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
    public PatientResource createPatient(SavePatientResource savePatientResource) {
        final Medic assignMedic = medicRepository.findById(savePatientResource.getMedicId()).orElseThrow(()->
            new NotFoundException("Medic","Id",savePatientResource.getMedicId())
        );


        if(patientRepository.findByDni(savePatientResource.getDni()).isPresent()){
            throw new DniExistsException("El DNI ya está asociado a otra cuenta");
        }else if(patientRepository.findByEmail(savePatientResource.getEmail()).isPresent()){
            throw new EmailExistsException("El email ya está asociado a otra cuenta");
        }else if(patientRepository.findByPhone(savePatientResource.getPhone()).isPresent()){
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
        Patient savePatient = patientRepository.save(newPatient);
        return mapper.map(savePatient,PatientResource.class);
    }

    @Override
    public PatientResource updatePatient(SavePatientResource savePatientResource, Long patientId){
        final Medic assignMedic = medicRepository.findById(savePatientResource.getMedicId()).orElseThrow(()->
                new NotFoundException("Medic","Id",savePatientResource.getMedicId())
        );

        Patient updatePatient = getPatientByID(patientId);
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

    public Patient getPatientByID(Long id){
        return patientRepository.findById(id).orElseThrow(()->
                new NotFoundException("Patient","Id",id)
        );
    }
}
