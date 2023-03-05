package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MedicRepository extends JpaRepository<Medic,Long> {

    Optional<Medic> findMedicByFullName(String fullName);
    Optional<Medic> findMedicByEmail(String email);

    Optional<Medic> findMedicByCmp(String cmp);

}
