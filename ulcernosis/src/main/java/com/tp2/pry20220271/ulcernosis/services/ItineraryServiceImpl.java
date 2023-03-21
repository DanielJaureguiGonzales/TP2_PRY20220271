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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {


    private final static ModelMapper mapper = new ModelMapper();
    private final ItineraryRepository itineraryRepository;
    private final NurseRepository nurseRepository;
    private final TeamWorkRepository teamWorkRepository;

    @Override
    public List<ItineraryResource> findAllItineraryByNurseId(Long nurseId) {
        List<Itinerary> itineraries = itineraryRepository.findAllByNurseId(nurseId);
        return itineraries.stream().map(itinerary -> mapper.map(itinerary, ItineraryResource.class)).collect(Collectors.toList());
    }

    @Override
    public ItineraryResource saveItinerary(SaveItineraryResource itinerary) {
        Nurse nurse = nurseRepository.findById(itinerary.getNurseId()).orElseThrow(() -> new NotFoundException("Nurse","Id",itinerary.getNurseId()));
        if (!teamWorkRepository.existsByNurseId(itinerary.getNurseId()))
            throw new TeamWorkExistsException("El enfermero no está asignado a ningún equipo médico, por favor asignelo en uno.");

        var itineraryToSave = Itinerary.builder()
                .timeIn(itinerary.getTimeIn())
                .timeOut(itinerary.getTimeOut())
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
