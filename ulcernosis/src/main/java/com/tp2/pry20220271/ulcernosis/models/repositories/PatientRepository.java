package com.tp2.pry20220271.ulcernosis.models.repositories;


import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByMedicId(Long medicId);


    Optional<Patient> findByFullName( String patientName);

    Optional<Patient> findByDni(String dni);
    Optional<Patient> findByPhone(String phone);
    Optional<Patient> findByEmail(String email);



}
