package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Diagnosis;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import com.tp2.pry20220271.ulcernosis.models.repositories.DiagnosisRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.services.DiagnosisService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private static final ModelMapper mapper = new ModelMapper();


    private final RestTemplate restTemplate;


    private final DiagnosisRepository diagnosisRepository;


    private final PatientRepository patientRepository;

    private final MedicRepository medicRepository;

    private final NurseRepository nurseRepository;

    @Value("${cnn.server.url}")
    private String urlCNN;


    @Override
    public DiagnosisResource findById(Long diagnosticId) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosticId).orElseThrow(() -> new NotFoundException("Diagnosis", "id", diagnosticId));
        return mapper.map(diagnosis, DiagnosisResource.class);
    }

    @Override
    public List<DiagnosisResource> findAllByPatientName(String patientName) {
        Patient patient = patientRepository.findByFullName(patientName).orElseThrow(() -> new NotFoundException("Patient", "name", patientName));
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByPatientId(patient.getId());
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisResource> findAllByNurseFullname(String nurseName) {
        Nurse nurse = nurseRepository.findNurseByFullName(nurseName).orElseThrow(() -> new NotFoundException("Nurse", "name", nurseName));
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByCreatorIdAndCreatorType(nurse.getId(), Type.NURSE);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisResource> findAllByMedicFullname(String medicName) {
        Medic medic = medicRepository.findMedicByFullName(medicName).orElseThrow(() -> new NotFoundException("Medic", "name", medicName));
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByCreatorIdAndCreatorType(medic.getId(), Type.MEDIC);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisResource> findAllByStagePredicted(String stagePredicted) {
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByStagePredicted(stagePredicted);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }


    /*@Override
    public List<DiagnosisResource> findAllByCreatorIdAndCreatorTypeAndStagePredicted(Long creatorId, Type creatorType, String stagePredicted) {
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByStagePredictedAndCreatorIdAndCreatorType(stagePredicted,creatorId, creatorType );
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisResource> findAllByCreatorIdAndCreatorType(Long creatorId, Type creatorType){
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByCreatorIdAndCreatorType(creatorId, creatorType);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }*/


    @Override
    public DiagnosisResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws IOException {
        Patient patient = patientRepository.findById(saveDiagnosisResource.getPatientId()).orElseThrow(() -> new NotFoundException("Patient", "id", saveDiagnosisResource.getPatientId()));
        DiagResource response =getDiagResourceCNN(file);
        Diagnosis diagnosis = new Diagnosis();
        if (saveDiagnosisResource.getCreatorType()== Type.MEDIC){
            Medic medic = medicRepository.findById(saveDiagnosisResource.getCreatorId()).orElseThrow(() -> new NotFoundException("Medic", "id", saveDiagnosisResource.getCreatorId()));
            diagnosis.setUlcerPhoto(file.getBytes());
            diagnosis.setCreatorId(medic.getId());
            diagnosis.setStage1(response.getStage_1());
            diagnosis.setStage2(response.getStage_2());
            diagnosis.setStage3(response.getStage_3());
            diagnosis.setStage4(response.getStage_4());
            diagnosis.setStagePredicted(response.getStage_predicted());
            diagnosis.setPatient(patient);
            diagnosis.setCreatorType(Type.MEDIC);
        } else if (saveDiagnosisResource.getCreatorType()== Type.NURSE){
            Nurse nurse = nurseRepository.findById(saveDiagnosisResource.getCreatorId()).orElseThrow(() -> new NotFoundException("Nurse", "id", saveDiagnosisResource.getCreatorId()));
            diagnosis.setUlcerPhoto(file.getBytes());
            diagnosis.setCreatorId(nurse.getId());
            diagnosis.setStage1(response.getStage_1());
            diagnosis.setStage2(response.getStage_2());
            diagnosis.setStage3(response.getStage_3());
            diagnosis.setStage4(response.getStage_4());
            diagnosis.setStagePredicted(response.getStage_predicted());
            diagnosis.setPatient(patient);
            diagnosis.setCreatorType(Type.NURSE);
        }

        return mapper.map(diagnosisRepository.save(diagnosis), DiagnosisResource.class);

    }


    @Override
    public String deleteDiagnosisById(Long diagnosticId) {
        diagnosisRepository.findById(diagnosticId).orElseThrow(() -> new NotFoundException("Diagnosis", "id", diagnosticId));
        diagnosisRepository.deleteById(diagnosticId);
        return "El diagnóstico ha sido eliminado";
    }

    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(()->
                new NotFoundException("Diagnosis","id",id)
        );
    }

    public DiagResource getDiagResourceCNN(MultipartFile file) throws  IOException {
        MultiValueMap<String,Object> body= new LinkedMultiValueMap<>();
        body.add("image",new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename(){
                return file.getOriginalFilename();
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        DiagResource response = restTemplate.postForObject(urlCNN,request,DiagResource.class);
        return response;
    }


}
