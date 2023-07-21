package com.tp2.pry20220271.ulcernosis.service;

import com.tp2.pry20220271.ulcernosis.models.entities.Medic;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import com.tp2.pry20220271.ulcernosis.models.entities.TeamWork;
import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import com.tp2.pry20220271.ulcernosis.models.repositories.MedicRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveMedicResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveNurseResource;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveTeamWorkResource;
import com.tp2.pry20220271.ulcernosis.resources.response.MedicResource;
import com.tp2.pry20220271.ulcernosis.resources.response.NurseResource;
import com.tp2.pry20220271.ulcernosis.resources.response.TeamWorkResource;
import com.tp2.pry20220271.ulcernosis.services.MedicServiceImpl;
import com.tp2.pry20220271.ulcernosis.services.NurseServiceImpl;
import com.tp2.pry20220271.ulcernosis.services.TeamWorkServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;


public class TeamWorkServiceTest {

    public static final Long idMedic = 1L;
    public static final Long idNurse = 1L;

    public static final List<Patient> patients = new ArrayList<>();
    public static final List<TeamWork> teamWorks = new ArrayList<>();

    public static Medic medic = new Medic();
    public static Nurse nurse = new Nurse();
    public static TeamWorkResource teamWorkResource = new TeamWorkResource();
    public static MedicResource medicResource = new MedicResource();
    public static NurseResource nurseResource = new NurseResource();
    public static SaveTeamWorkResource saveTeamWorkResource = new SaveTeamWorkResource();
    public static SaveMedicResource saveMedicResource = new SaveMedicResource();
    public static SaveNurseResource saveNurseResource = new SaveNurseResource();

    public static Optional<Medic> optionalMedic;
    public static Optional<Nurse> optionalNurse;
            ;
    public static TeamWork teamWork = new TeamWork();

    @Mock
    public TeamWorkRepository teamWorkRepository;

    @Mock
    public ModelMapper modelMapper;

    @Mock
    public PasswordEncoder passwordEncoder;

    @Mock
    public MedicRepository medicRepository;
    @Mock
    public NurseRepository nurseRepository;

    @InjectMocks
    public TeamWorkServiceImpl teamWorkService;
    @InjectMocks
    public MedicServiceImpl medicService;
    @InjectMocks
    public NurseServiceImpl nurseService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        medic.setId(2L);
        medic.setFullName("daniel jauregui");
        medic.setAge(25);
        medic.setDni("4567891");
        medic.setAddress("independencia");
        medic.setCivilStatus("soltero");
        medic.setCreatedAt(new Date());
        medic.setEmail("daniel.jauregui@gmail.com");
        medic.setPassword("jpo58#45");
        medic.setPhone("985784965");
        medic.setAvatar(new byte[]{});
        medic.setRole(Role.ROLE_MEDIC);
        medic.setCmp("012345");
        medic.setTeamWorks(teamWorks);
        medic.setPatients(patients);

        nurse.setId(2L);
        nurse.setFullName("daniel jauregui");
        nurse.setAge(25);
        nurse.setDni("4567891");
        nurse.setAddress("independencia");
        nurse.setCivilStatus("soltero");
        nurse.setCreatedAt(new Date());
        nurse.setEmail("daniel.jauregui@gmail.com");
        nurse.setPassword("jpo58#45");
        nurse.setPhone("985784965");
        nurse.setAvatar(new byte[]{});
        nurse.setRole(Role.ROLE_MEDIC);
        nurse.setCep("123456");
        nurse.setTeamWork(teamWorks);
        nurse.setHaveTeamWork(false);
        nurse.setIsAuxiliar(false);
        nurse.setItWasNotified(false);

        teamWork.setId(1L);
        teamWork.setMedic(medic);
        teamWork.setNurse(nurse);

        teamWorkResource.setId(1L);
        teamWorkResource.setMedicId(medic.getId());
        teamWorkResource.setNurseId(nurse.getId());

        saveTeamWorkResource.setMedicId(2L);
        saveTeamWorkResource.setNurseId(2L);

        saveMedicResource.setFullName("daniel jauregui");
        saveMedicResource.setAge("25");
        saveMedicResource.setDni("4567891");
        saveMedicResource.setAddress("independencia");
        saveMedicResource.setCivilStatus("soltero");
        saveMedicResource.setEmail("daniel.jauregui@gmail.com");
        saveMedicResource.setPassword("jpo58#45");
        saveMedicResource.setPhone("985784965");
        saveMedicResource.setRole(Role.ROLE_MEDIC);
        saveMedicResource.setCmp("012345");


         medicResource.setId(2L);
         medicResource.setFullName("daniel jauregui");
         medicResource.setAge(25);
         medicResource.setDni("4567891");
         medicResource.setAddress("independencia");
         medicResource.setCivilStatus("soltero");
         medicResource.setCreatedAt(new Date());
         medicResource.setEmail("daniel.jauregui@gmail.com");
         medicResource.setPhone("985784965");
         medicResource.setRole(Role.ROLE_MEDIC);
         medicResource.setCmp("012345");

        saveNurseResource.setFullName("daniel jauregui");
        saveNurseResource.setAge(25);
        saveNurseResource.setDni("4567891");
        saveNurseResource.setAddress("independencia");
        saveNurseResource.setCivilStatus("soltero");
        saveNurseResource.setEmail("daniel.jauregui@gmail.com");
        saveNurseResource.setPassword("jpo58#45");
        saveNurseResource.setPhone("985784965");
        saveNurseResource.setRole(Role.ROLE_MEDIC);
        saveNurseResource.setCep("123456");
        saveNurseResource.setIsAuxiliar(false);

        nurseResource.setId(2L);
        nurseResource.setFullName("daniel jauregui");
        nurseResource.setAge(25);
        nurseResource.setDni("4567891");
        nurseResource.setAddress("independencia");
        nurseResource.setCivilStatus("soltero");
        nurseResource.setCreatedAt(new Date());
        nurseResource.setEmail("daniel.jauregui@gmail.com");
        nurseResource.setPhone("985784965");
        nurseResource.setRole(Role.ROLE_MEDIC);
        nurseResource.setCep("123456");
        nurseResource.setHaveTeamWork(false);
        nurseResource.setIsAuxiliar(false);
        nurseResource.setItWasNotified(false);

        optionalMedic = Optional.of(medic);
        optionalNurse = Optional.of(nurse);
    }

    @DisplayName("Test para crear un equipo de trabajo entre médico y enfermero")
    @Test
    void createTeamWork(){


       BDDMockito.given(medicRepository.findById(saveTeamWorkResource.getMedicId())).willReturn(Optional.of(medic));
       BDDMockito.given(nurseRepository.findById(saveTeamWorkResource.getNurseId())).willReturn(Optional.of(nurse));
       List<TeamWork> teamWorksByMedicExists = new ArrayList<>();
       BDDMockito.given(teamWorkRepository.findAllByMedicId(medic.getId())).willReturn(teamWorksByMedicExists);
       BDDMockito.given(teamWorkRepository.save(Mockito.any(TeamWork.class))).willReturn(new TeamWork());

        TeamWorkResource teamWorkResource = teamWorkService.createTeamWork(saveTeamWorkResource);

        // Verificar que el teamWorkRepository.save() se haya llamado
        Mockito.verify(teamWorkRepository).save(Mockito.any(TeamWork.class));

        Assertions.assertNotNull(teamWorkResource);


    }

}
