package com.epam.mentoring.service;

import com.epam.mentoring.model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {
	public boolean addEvent(Event event);
	
	public boolean removeEventById(Long eventId);
	
	public Event findEventById(long eventId);
	
	public List<Event> findEventsByTitle(String title);
	
	public List<Event> findEventsByDate(Date date);
	
	public boolean updateEvent(Event event);
}
