package com.epam.rest;

import java.util.List;
import java.util.Optional;

import com.epam.rest.repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    @Autowired
    private EventRepo eventRepo;

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable("eventId") long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        return event;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(@RequestParam(value = "title", required = false) String title) {
        List<Event> events;
        if (title == null) {
            events = eventRepo.findAll();
        } else {
            events = eventRepo.findAllByTitleContaining(title);
        }
        return events;
    }


    @DeleteMapping("/events/{eventId}")
    public Event deleteEvent(@PathVariable("eventId") long eventId) {
        Optional<Event> event = eventRepo.findById(eventId);
        if(event.isPresent()) {
            eventRepo.deleteById(eventId);
            return event.get();
        }
        return null;
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        Event newEvent = eventRepo.save(event);
        return newEvent;
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@RequestBody Event event, @PathVariable("eventId") long eventId) {
        event.setId(eventId);
        eventRepo.save(event);
        return event;
    }
}