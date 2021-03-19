package com.epam.rest.service;

import com.epam.rest.entity.Event;

import java.util.List;

public interface EventService {
    public Event createEvent(Event event);
    public Event updateEvent(Event event, long eventId);
    public Event getEvent(long eventId);
    public Event deleteEvent(long eventId);
    public List<Event> getAllEvents();
    public List<Event> getAllEventsByTitle(String title);
}
