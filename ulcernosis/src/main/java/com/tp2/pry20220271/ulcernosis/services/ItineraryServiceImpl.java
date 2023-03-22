package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.exceptions.TeamWorkExistsException;
import com.tp2.pry20220271.ulcernosis.models.entities.Itinerary;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.repositories.ItineraryRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.TeamWorkRepository;
import com.tp2.pry20220271.ulcernosis.models.services.ItineraryService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.response.ItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateItineraryResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {


    private final static ModelMapper mapper = new ModelMapper();
    private final ItineraryRepository itineraryRepository;
    private final NurseRepository nurseRepository;
    private final TeamWorkRepository teamWorkRepository;

    @Override
    public ItineraryResource findItineraryByNurseId(Long nurseId) {
        Itinerary itineraries = itineraryRepository.findByNurseId(nurseId);
        return mapper.map(itineraries, ItineraryResource.class);
    }

    @Override
    public ItineraryResource saveItinerary(SaveItineraryResource itinerary, Long nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(() -> new NotFoundException("Nurse","Id",nurseId));

        if (!teamWorkRepository.existsByNurseId(nurseId))
            throw new TeamWorkExistsException("El enfermero no está asignado a ningún equipo médico, por favor asignelo en uno.");
        if (itineraryRepository.existsByNurseId(nurseId))
            throw new TeamWorkExistsException("El enfermero ya tiene un itinerario asignado, por favor actualice el itinerario existente.");

        var itineraryToSave = Itinerary.builder()
                .timeIn(itinerary.getTimeIn())
                .timeOut((itinerary.getTimeOut()))
                .nurse(nurse)
                .build();

        return mapper.map(itineraryRepository.save(itineraryToSave), ItineraryResource.class);
    }

    @Override
    public ItineraryResource updateItinerary(Long itineraryId, UpdateItineraryResource itinerary) {
        Itinerary updateItinerary = itineraryRepository.findById(itineraryId).orElseThrow(() -> new NotFoundException("Itinerary","Id",itineraryId));
        updateItinerary.setTimeIn(itinerary.getTimeIn());
        updateItinerary.setTimeOut(itinerary.getTimeOut());
        return mapper.map(itineraryRepository.save(updateItinerary), ItineraryResource.class);
    }


}
