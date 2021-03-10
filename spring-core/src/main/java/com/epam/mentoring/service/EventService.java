package com.epam.mentoring.service;

import java.util.Date;
import java.util.List;

import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;

public interface EventService {
	public void addEvent(Event event) throws EntryExistsAlreadyException, EntryValidationException;
	
	public Event removeEventById(Long eventId) throws EntryNotFoundException;
	
	public Event findEventById(long eventId) throws EntryNotFoundException;
	
	public List<Event> findEventsByTitle(String title) throws EntryNotFoundException;
	
	public List<Event> findEventsByDate(Date date) throws EntryNotFoundException;
	
	public Event updateEvent(Event event) throws EntryNotFoundException, EntryValidationException;
}
