package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.resources.request.SaveItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.response.ItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateItineraryResource;

import java.util.List;

public interface ItineraryService {

    ItineraryResource saveItinerary(SaveItineraryResource itinerary);
    ItineraryResource updateItinerary(Long itineraryId, UpdateItineraryResource itinerary);
    List<ItineraryResource> findAllItineraryByNurseId(Long nurseId);

}
