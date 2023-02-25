package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<PatientResource> findAllPatientsByMedicId(Long medicId) throws UlcernosisException {
        final Medic searchMedic = medicRepository.findById(medicId).orElseThrow(()-> new NotFoundException("UCN_404","NOT_FOUND_EXCEPTION"));
        List<Patient> patients_founds = patientRepository.findAllByMedicId(searchMedic.getId());
        return patients_founds.stream().map(patient_found-> mapper.map(patient_found,PatientResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<PatientResource> findAllByNurseId(Long nurseId) throws UlcernosisException {
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(()-> new NotFoundException("UCN-404","NURSE_NOT_FOUND"));
        List<Assignment> assignments = assignmentRepository.findAllByNurseId(nurse.getId());
        List<Patient> patients = patientRepository.findAllByAssignmentsIn(assignments);
        return patients.stream().map(patient -> mapper.map(patient,PatientResource.class)).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public PatientResource findPatientById(Long id) throws UlcernosisException {
        return mapper.map(getPatientByID(id), PatientResource.class);
    }

    @Override
    public Resource findPatientPhoto(Long id) throws UlcernosisException {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","USER_NOT_FOUND"));
        Resource avatar = new ByteArrayResource(patient.getAvatar());
        return avatar;
    }

    @Override
    public Resource putPatientPhoto(Long id, MultipartFile file) throws UlcernosisException, IOException{
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","USER_NOT_FOUND"));

        try {
            patient.setAvatar(file.getBytes());
            patientRepository.save(patient);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }

        Resource avatar = new ByteArrayResource(patient.getAvatar());
        return avatar;
    }

    @Override
    public PatientResource createPatient(SavePatientResource savePatientResource) throws UlcernosisException, IOException {
        final Medic assignMedic = medicRepository.findById(savePatientResource.getMedicId()).orElseThrow(()->
            new NotFoundException("UCN-404","MEDIC_NOT_FOUND")
        );

        Patient newPatient = new Patient();
        newPatient.setFullName(savePatientResource.getFullName());
        newPatient.setAge(savePatientResource.getAge());
        newPatient.setEmail(savePatientResource.getEmail());
        newPatient.setAddress(savePatientResource.getAddress());
        newPatient.setDni(savePatientResource.getDni());
        newPatient.setAvatar(new byte[]{});
        newPatient.setCivilStatus(savePatientResource.getCivilStatus());
        newPatient.setMedic(assignMedic);
        Long id;
        try {
            id = patientRepository.save(newPatient).getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        PatientResource patientResource = mapper.map(newPatient,PatientResource.class);

        log.info(String.valueOf(patientResource.getMedicId()));
        return  patientResource;
    }

    @Override
    public PatientResource updatePatient(SavePatientResource savePatientResource, Long patientId) throws UlcernosisException, IOException {
        final Medic assignMedic = medicRepository.findById(savePatientResource.getMedicId()).orElseThrow(()->
                new NotFoundException("UCN-404","MEDIC_NOT_FOUND")
        );

        Patient updatePatient = getPatientByID(patientId);
        updatePatient.setFullName(savePatientResource.getFullName());
        updatePatient.setAge(savePatientResource.getAge());
        updatePatient.setEmail(savePatientResource.getEmail());
        updatePatient.setAddress(savePatientResource.getAddress());
        updatePatient.setDni(savePatientResource.getDni());
        updatePatient.setCivilStatus(savePatientResource.getCivilStatus());

        Long id;
        try {
            id = patientRepository.save(updatePatient).getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        PatientResource patientResource = mapper.map(updatePatient,PatientResource.class);

        return  patientResource;
    }

    @Override
    public String deletePatient(Long patientId) throws UlcernosisException {
        Patient patient = getPatientByID(patientId);
        try {
            patientRepository.delete(patient);
        } catch (Exception e){
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        return "Se eliminó al paciente exitosamente";
    }

    public Patient getPatientByID(Long id) throws UlcernosisException {
        return patientRepository.findById(id).orElseThrow(()->
                new NotFoundException("UCN-404","USER_NOT_FOUND")
        );
    }
}
