package com.tp2.pry20220271.ulcernosis.models.repositories;



import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface NurseRepository extends JpaRepository<Nurse,Long> {

    List<Nurse> findAllByHaveTeamWork(Boolean isHaveTeamWork);
    List<Nurse> findAllByTeamWorkIn(List<TeamWork> teamWorks);
    Optional<Nurse> findNurseByCep(String cep);
    Optional<Nurse> findNurseByEmail(String email);
    Optional<Nurse> findNurseByFullName(String fullName);

    /*List<Nurse> findAllByIsAssigned(Boolean isAssigned);*/
    List<Nurse> findAllByIsAuxiliar(Boolean isAuxiliar);

}
