package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

    List<Assignment> findAllByPatientId(Long patientId);
    List<Assignment> findAllByNurseId(Long nurseId);
    Optional<Assignment> findByPatientId(Long patientId);
    Optional<Assignment> findByPatientIdAndNurseId(Long patientId, Long nurseId);
    void deleteAllByNurseId(Long nurseId);
}
