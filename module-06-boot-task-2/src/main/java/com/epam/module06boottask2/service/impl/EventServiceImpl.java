package com.epam.module06boottask2.service.impl;

import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.exception.EventNotFoundException;
import com.epam.module06boottask2.repository.EventRepo;
import com.epam.module06boottask2.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepo eventRepo;

    public Event createEvent(Event event) {
        return eventRepo.save(event);
    }

    @Transactional
    public Event updateEvent(Event event, long eventId) {
        eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        event.setId(eventId);
        eventRepo.save(event);
        return event;
    }

    public Event getEvent(long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    }

    @Transactional
    public Event deleteEvent(long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        eventRepo.deleteById(eventId);
        return event;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public List<Event> getAllEventsByTitle(String title) {
        return eventRepo.findAllByTitleContaining(title);
    }
}
