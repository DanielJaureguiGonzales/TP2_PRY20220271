package com.tp2.pry20220271.ulcernosis.models.services;

import com.tp2.pry20220271.ulcernosis.resources.request.SaveItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.response.ItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateItineraryResource;

import java.util.List;

public interface ItineraryService {

    ItineraryResource saveItinerary(SaveItineraryResource itinerary, Long nurseId);
    ItineraryResource updateItinerary(Long itineraryId, UpdateItineraryResource itinerary);
    ItineraryResource findItineraryByNurseId(Long nurseId);

}
