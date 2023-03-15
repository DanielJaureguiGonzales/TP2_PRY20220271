package com.tp2.pry20220271.ulcernosis.models.repositories;

import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamWorkRepository extends JpaRepository<TeamWork,Long> {


    List<TeamWork> findAllByNurseId(Long nurseId);

    List<TeamWork> findAllByMedicId(Long medicId);

    void deleteTeamWorkByNurseId(Long nurseId);

    Optional<TeamWork> findByNurseId(Long id);
    Optional<TeamWork> findByNurseIdAndMedicId(Long nurseId, Long medicId);

    boolean existsByMedicIdAndNurseId(Long medicId, Long nurseId);
}
