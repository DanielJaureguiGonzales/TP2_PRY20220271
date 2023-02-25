package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Diagnostic;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.repositories.DiagnosticRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.PatientRepository;
import com.tp2.pry20220271.ulcernosis.models.services.DiagnosticService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveDiagnosisResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagResource;
import com.tp2.pry20220271.ulcernosis.resources.response.DiagnosticResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
public class DiagnosticServiceImpl implements DiagnosticService {

    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private DiagnosticRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    public DiagnosticServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<DiagnosticResource> findAllByPatientId(Long patientId) throws UlcernosisException {
        List<Diagnostic> diagnosticList = diagnosisRepository.findAllByPatientId(patientId);
        return diagnosticList.stream().map(diagnostic -> mapper.map(diagnostic, DiagnosticResource.class)).collect(Collectors.toList());
    }

    @Override
    public List<DiagnosticResource> findAllByCreatorType(String creatorType) throws UlcernosisException {
        return null;
    }

    @Override
    public List<DiagnosticResource> findAllByStage1() throws UlcernosisException {
        return null;
    }

    @Override
    public List<DiagnosticResource> findAllByStage2() throws UlcernosisException {
        return null;
    }

    @Override
    public List<DiagnosticResource> findAllByStage3() throws UlcernosisException {
        return null;
    }

    @Override
    public List<DiagnosticResource> findAllByStage4() throws UlcernosisException {
        return null;
    }

    @Override
    public DiagnosticResource saveDiagnosis(SaveDiagnosisResource saveDiagnosisResource, MultipartFile file) throws UlcernosisException, IOException {

        DiagResource response =getDiagResourceCNN(file);
        System.out.println(response.getStage_1());
        System.out.println(response.getStage_2());
        System.out.println(response.getStage_3());
        System.out.println(response.getStage_4());
        System.out.println(response.getStage_predicted());
        Diagnostic newDiagnostic = new Diagnostic();

        Patient patient = patientRepository.findById(saveDiagnosisResource.getPatientId()).orElseThrow(() -> new NotFoundException("UCN-404","PATIENT_NOT_FOUND"));


        newDiagnostic.setCreatorId(saveDiagnosisResource.getCreatorId());
        newDiagnostic.setStage1(response.getStage_1());
        newDiagnostic.setStage2(response.getStage_2());
        newDiagnostic.setStage3(response.getStage_3());
        newDiagnostic.setStage4(response.getStage_4());
        newDiagnostic.setStagePredicted(response.getStage_predicted());
        newDiagnostic.setPatient(patient);
        newDiagnostic.setUlcerPhoto(file.getBytes());
        Long id;
        //TODO: REALIZAR EL GUARDADO DE LA ENTIDAD A LA BASE DE DATOS (FALTA SU CONTROLADOR)
        try {
            id = diagnosisRepository.save(newDiagnostic).getId();
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }

        return mapper.map(getDiagnosticById(id),DiagnosticResource.class);
    }


    @Override
    public String deleteDiagnosisById(Long diagnosticId) throws UlcernosisException {
        return null;
    }

    public Diagnostic getDiagnosticById(Long id) throws UlcernosisException {
        return diagnosisRepository.findById(id).orElseThrow(()->
                new NotFoundException("UCN-404","MEDIC_NOT_FOUND")
        );
    }

    public DiagResource getDiagResourceCNN(MultipartFile file) throws UlcernosisException {
        MultiValueMap<String,Object> body= new LinkedMultiValueMap<>();
        try {
            body.add("image",new ByteArrayResource(file.getBytes()){
                @Override
                public String getFilename(){
                    return file.getOriginalFilename();
                }
            });
        }catch (Exception E){
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        DiagResource response = restTemplate.postForObject("http://127.0.0.1:5000/predict",request,DiagResource.class);
        return response;
    }
}
