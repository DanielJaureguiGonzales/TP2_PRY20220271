package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {


    List<Diagnosis> findAllByCreatorId(String creatorId);

    List<Diagnosis> findAllByPatientId(Long patientId);

    @Query("select d from diagnosis d where d.stagePredicted = '1'")
    List<Diagnosis> findAllByStage1();

    @Query("select d from diagnosis d where d.stagePredicted = '2'")
    List<Diagnosis> findAllByStage2();

    @Query("select d from diagnosis d where d.stagePredicted = '3'")
    List<Diagnosis> findAllByStage3();

    @Query("select d from diagnosis d where d.stagePredicted = '4'")
    List<Diagnosis> findAllByStage4();
}
