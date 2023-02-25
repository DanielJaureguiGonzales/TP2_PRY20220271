package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticRepository extends JpaRepository<Diagnostic,Long> {

    List<Diagnostic> findAllByCreatorId(String creatorId);

    List<Diagnostic> findAllByPatientId(Long patientId);

    @Query("select d from diagnosis d where d.stagePredicted = 1")
    List<Diagnostic> findAllByStage1();

    @Query("select d from diagnosis d where d.stagePredicted = 2")
    List<Diagnostic> findAllByStage2();

    @Query("select d from diagnosis d where d.stagePredicted = 3")
    List<Diagnostic> findAllByStage3();

    @Query("select d from diagnosis d where d.stagePredicted = 4")
    List<Diagnostic> findAllByStage4();
}
