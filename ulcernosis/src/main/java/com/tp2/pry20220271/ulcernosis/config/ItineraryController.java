package com.tp2.pry20220271.ulcernosis.config;

import com.tp2.pry20220271.ulcernosis.models.services.ItineraryService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.response.ItineraryResource;
import com.tp2.pry20220271.ulcernosis.resources.updates.UpdateItineraryResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping("/get-by-nurse/{nurseId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ItineraryResource> getItineraryByNurseId(Long nurseId) {
        return itineraryService.findAllItineraryByNurseId(nurseId);
    }

    @PostMapping("/create-itinerary")
    @ResponseStatus(HttpStatus.CREATED)
    public ItineraryResource createItinerary(@RequestBody @Valid SaveItineraryResource itineraryResource) {
        return itineraryService.saveItinerary(itineraryResource);
    }

    @PutMapping("/update-itinerary/{itineraryId}")
    @ResponseStatus(HttpStatus.OK)
    public ItineraryResource updateItinerary(@PathVariable Long itineraryId, @RequestBody @Valid UpdateItineraryResource itineraryResource) {
        return itineraryService.updateItinerary(itineraryId, itineraryResource);
    }
}
