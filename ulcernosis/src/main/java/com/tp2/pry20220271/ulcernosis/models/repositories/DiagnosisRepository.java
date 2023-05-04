package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Diagnosis;
import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {


    List<Diagnosis> findAllByCreatorIdAndCreatorType(Long creatorId, Type creatorType);
    List<Diagnosis> findAllByCreatorIdAndCreatorTypeAndStagePredicted(Long creatorId, Type creatorType, String stagePredicted);
    List<Diagnosis> findAllByCreatorIdInAndCreatorType(List<Long> creatorsId, Type creatorType);
    List<Diagnosis> findAllByCreatorIdInAndCreatorTypeInAndStagePredicted(List<Long> creatorsId, List<Type> creatorType, String stagePredicted);
    List<Diagnosis> findAllByCreatorIdInAndCreatorTypeIn(List<Long> creatorsId, List<Type> creatorType);
    List<Diagnosis> findAllByPatientId(Long patientId);

    List<Diagnosis> findAllByStagePredicted(String stagePredicted);
    Diagnosis findByCreatorIdAndPatientIdAndIsConfirmed(Long creatorId, Long patientId, Boolean isConfirmed);
    boolean existsByCreatorIdAndPatientIdAndIsConfirmed(Long creatorId, Long patientId, Boolean isConfirmed);
   /* boolean existsByCreatorIdAndPatientIdAndCreatedAt(Long creatorId, Long patientId, Date createdAt);
    *//*List<Diagnosis> findAllByStagePredictedAndCreatorIdAndCreatorType(String stagePredicted,Long creatorId, Type creatorType);*//*
    boolean existsByCreatorIdAndCreatorType(Long creatorId, Type creatorType);*/
}
