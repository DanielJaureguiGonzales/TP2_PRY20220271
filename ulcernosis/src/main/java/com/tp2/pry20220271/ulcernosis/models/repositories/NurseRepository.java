package com.tp2.pry20220271.ulcernosis.models.repositories;


import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NurseRepository extends JpaRepository<Nurse,Long> {

    List<Nurse> findAllByTeamWorkIn(List<TeamWork> teamWorks);

}
