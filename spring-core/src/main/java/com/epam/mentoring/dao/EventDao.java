package com.epam.mentoring.dao;

import com.epam.mentoring.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventDao {
	private static Logger logger = LoggerFactory.getLogger(EventDao.class);
	private Storage storage;

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public void addEvent(Event event) {
		logger.debug("addEvent({}) call.", event);
		storage.getEvents().put(event.getId(), event);
	}
	
	public Event removeEventById(Long eventId) {
		logger.debug("removeEventById({}) call.", eventId);
		return storage.getEvents().remove(eventId);
	}
	
	public Event findEventById(long eventId) {
		logger.debug("findEventById({}) call.", eventId);
		return storage.getEvents().get(eventId);
	}
	
	public Event updateEvent(Event event) {
		logger.debug("updateEvent({}) call.", event);
		storage.getEvents().put(event.getId(), event);
		return event;
	}
	
	public List<Event> findEventsByTitle(String title) {
		logger.debug("findEventsByTitle({}) call.", title);
		return storage.getEvents().values().stream()
				.filter(event -> event.getTitle().contains(title))
				.collect(Collectors.toList());
	}
	
	public List<Event> findEventsByDate(Date date) {
		logger.debug("findEventsByDate({}) call.", date);
		return storage.getEvents().values().stream()
				.filter(event -> trimTime(event.getDate()).equals(trimTime(date)))
				.collect(Collectors.toList());
	}
	
	private LocalDate trimTime(Date date) {
		return date.toInstant().atZone(ZoneId.of("Etc/GMT-2")).toLocalDate();
	}

}
