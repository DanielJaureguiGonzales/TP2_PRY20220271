package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Diagnosis;
import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {


    List<Diagnosis> findAllByCreatorIdAndCreatorType(Long creatorId, Type creatorType);

    List<Diagnosis> findAllByPatientId(Long patientId);

    List<Diagnosis> findAllByStagePredicted(String stagePredicted);
    /*List<Diagnosis> findAllByStagePredictedAndCreatorIdAndCreatorType(String stagePredicted,Long creatorId, Type creatorType);*/
    boolean existsByCreatorIdAndCreatorType(Long creatorId, Type creatorType);
}
