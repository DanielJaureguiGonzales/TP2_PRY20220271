package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.InternalServerException;
import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.UlcernosisException;
import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.services.MedicService;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
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
import java.util.stream.Collectors;

@Service
public class MedicServiceImpl implements MedicService {


    private static final Logger log = LoggerFactory.getLogger(MedicServiceImpl.class);

    @Autowired
    private MedicRepository medicRepository;

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<MedicResource> findAll() throws UlcernosisException {
        List<Medic> medicos = medicRepository.findAll();
        return medicos.stream().map(
                medic -> mapper.map(medic,MedicResource.class)
        ).collect(Collectors.toList());
    }

    @Override
    public MedicResource findMedicById(Long id) throws UlcernosisException {
       return mapper.map(getMedicByID(id), MedicResource.class);
    }

    @Override
    public MedicResource findMedicByFullName(String fullname) throws UlcernosisException {
        Medic foundMedic = medicRepository.findMedicByFullName(fullname).orElseThrow(()-> new NotFoundException("UCN-404","MEDIC_NOT_FOUND"));
        return mapper.map(foundMedic,MedicResource.class);
    }

    @Override
    public Resource findMedicPhoto(Long id) throws UlcernosisException, IOException {
        Medic medic = medicRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","MEDIC_NOT_FOUND"));
        Resource avatar = new ByteArrayResource(medic.getAvatar());
        return avatar;
    }

    @Override
    public Resource updateMedicPhoto(Long id, MultipartFile avatar) throws UlcernosisException, IOException {
        Medic medic = medicRepository.findById(id).orElseThrow(()-> new NotFoundException("UCN-404","MEDIC_NOT_FOUND"));
        medic.setAvatar(avatar.getBytes());
        Long id1;
        try {
            id1 = medicRepository.save(medic).getId();
        }catch (Exception e){
            throw new InternalServerException("UCN-500",e.getMessage());
        }
        Resource avatar1 = new ByteArrayResource(medic.getAvatar());
        return avatar1;
    }

    @Override
    public MedicResource saveMedic(SaveMedicResource saveMedicResource) throws UlcernosisException, IOException {
        Medic newMedic = mapper.map(saveMedicResource,Medic.class);

        newMedic.setAvatar(new byte[]{});
        newMedic.setDni(saveMedicResource.getDni());
        Long id;
        try {
            id=medicRepository.save(newMedic).getId();

        }catch (Exception a){

            log.error(a.getMessage());
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }

        MedicResource medicResource = mapper.map(getMedicByID(id), MedicResource.class);

        return medicResource;
    }

    @Override
    public MedicResource updateMedic(Long id, SaveMedicResource saveMedicResource) throws UlcernosisException, IOException {
        Medic updateMedic = getMedicByID(id);
        updateMedic.setDni(saveMedicResource.getDni());
        updateMedic.setAge(saveMedicResource.getAge());
        updateMedic.setEmail(saveMedicResource.getEmail());
        updateMedic.setFullName(saveMedicResource.getFullName());
        updateMedic.setCmp(saveMedicResource.getCmp());
        updateMedic.setCivilStatus(saveMedicResource.getCivilStatus());
        updateMedic.setAddress(saveMedicResource.getAddress());
        Long idUpdatedMedic;
        try {
            idUpdatedMedic = medicRepository.save(updateMedic).getId();
        }catch (Exception e) {
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }

        MedicResource medicResource = mapper.map(getMedicByID(idUpdatedMedic), MedicResource.class);

       return medicResource;
    }

    @Override
    public String deleteMedic(Long id) throws UlcernosisException {

        Medic deleteMedic = getMedicByID(id);

        try{
            medicRepository.delete(deleteMedic);
        }catch (Exception e){
            throw new InternalServerException("UCN-500","INTERNAL_SERVER_ERROR");
        }
        return "Se eliminó al médico exitosamente";
    }

    public Medic getMedicByID(Long id) throws UlcernosisException {
        return medicRepository.findById(id).orElseThrow(()->
            new NotFoundException("UCN-404","MEDIC_NOT_FOUND")
        );
    }
}
