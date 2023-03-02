package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Diagnosis;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.repositories.DiagnosisRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.services.DiagnosisService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosisResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DiagnosisServiceImpl implements DiagnosisService {

    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    public DiagnosisServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<DiagnosisResource> findAllByPatientId(Long patientId){
        List<Diagnosis> diagnosticList = diagnosisRepository.findAllByPatientId(patientId);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosisResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosisResource> findAllByCreatorType(String creatorType){
        return null;
    }

    @Override
    public List<DiagnosisResource> findAllByStage1() {
        return null;
    }

    @Override
    public List<DiagnosisResource> findAllByStage2(){
        return null;
    }

    @Override
    public List<DiagnosisResource> findAllByStage3() {
        return null;
    }

    @Override
    public List<DiagnosisResource> findAllByStage4(){
        return null;
    }

    @Override
    public DiagnosisResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws IOException {

        Patient patient = patientRepository.findById(saveDiagnosisResource.getPatientId()).orElseThrow(() -> new NotFoundException("Patient", "id", saveDiagnosisResource.getPatientId()));
        DiagResource response =getDiagResourceCNN(file);

        Diagnosis newDiagnosis = new Diagnosis();

        newDiagnosis.setCreatorId(saveDiagnosisResource.getCreatorId());
        newDiagnosis.setStage1(response.getStage_1());
        newDiagnosis.setStage2(response.getStage_2());
        newDiagnosis.setStage3(response.getStage_3());
        newDiagnosis.setStage4(response.getStage_4());
        newDiagnosis.setStagePredicted(response.getStage_predicted());
        newDiagnosis.setPatient(patient);
        newDiagnosis.setUlcerPhoto(file.getBytes());


        return mapper.map(diagnosisRepository.save(newDiagnosis), DiagnosisResource.class);
    }


    @Override
    public String deleteDiagnosisById(Long diagnosticId) {
        return null;
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
        DiagResource response = restTemplate.postForObject("http://127.0.0.1:5000/predict",request,DiagResource.class);
        return response;
    }
}
