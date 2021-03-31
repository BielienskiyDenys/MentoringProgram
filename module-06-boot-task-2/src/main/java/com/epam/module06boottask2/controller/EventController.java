package com.epam.module06boottask2.controller;

import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.repository.EventRepo;
import com.epam.module06boottask2.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventService eventService;

    @GetMapping("/events/{eventId}")
    public Event getEvent(@PathVariable("eventId") long eventId) {
        return eventService.getEvent(eventId);
    }

    @GetMapping("/events")
    public List<Event> getAllEventsByTitle(@RequestParam(value = "title", required = false) String title) {
        if(title == null) return eventService.getAllEvents();
        return eventService.getAllEventsByTitle(title);
    }


    @DeleteMapping("/events/{eventId}")
    public Event deleteEvent(@PathVariable("eventId") long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@RequestBody Event event, @PathVariable("eventId") long eventId) {
        return eventService.updateEvent(event, eventId);
    }
}
