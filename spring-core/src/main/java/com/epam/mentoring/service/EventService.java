package com.epam.mentoring.service;

import java.util.Date;
import java.util.List;

import com.epam.mentoring.model.Event;

public interface EventService {
	public boolean addEvent(Event event);
	
	public boolean removeEventById(Long eventId);
	
	public Event findEventById(long eventId);
	
	public List<Event> findEventsByTitle(String title);
	
	public List<Event> findEventsByDate(Date date);
	
	public boolean updateEvent(Event event);
}
