package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Appointment;
import com.tp2.pry20220271.ulcernosis.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByNurseId(Long nurseId);
    Appointment findByDiagnosisId(Long diagnosisId);

    Appointment findByNurseIdAndPatientIdAndStatus(Long nurseId, Long patientId, Status status);

}
