package com.epam.mentoring.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.User;
import com.epam.mentoring.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class EventServiceImpl implements EventService{
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private EventDao eventDao;
	
	public EventDao getEventDao() {
		return eventDao;
	}
	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	public void addEvent(Event event) throws EntryExistsAlreadyException, EntryValidationException {
		validateEventFields(event);

		if (eventDao.findEventById(event.getId()) == null) {
			logger.debug("addEvent({}) call. Readressing to repository.", event);
			eventDao.addEvent(event);
		} else {
			logger.error("addEvent({}) call. No event found. Throwing error.", event);
			throw new EntryExistsAlreadyException("Event already registered.");
		}
	}
	
	public Event removeEventById(Long eventId) throws EntryNotFoundException {
		Event event = eventDao.findEventById(eventId);
		if (event != null) {
			logger.debug("removeEventById({}) call. Readressing to repository.", eventId);
			return eventDao.removeEventById(eventId);
		}
		logger.error("removeEventById({}) call. No event found. Throwing error.", eventId);
		throw new EntryNotFoundException("Event not found.");
	}
	
	public Event findEventById(long eventId) throws EntryNotFoundException {
		Event event = eventDao.findEventById(eventId);
		if (event != null) {
			logger.debug("findEventById({}) call. Readressing to repository.", eventId);
			return event;
		}
		logger.error("findEventById({}) call. No event found. Throwing error.", eventId);
		throw new EntryNotFoundException("Event not found.");
	}
	
	public List<Event> findEventsByTitle(String title) throws EntryNotFoundException {
		List<Event> resultList = eventDao.findEventsByTitle(title);
		if(!resultList.isEmpty()) {
			logger.debug("findEventsByTitle({}) call. Readressing to repository.", title);
			return resultList;
		}
		logger.error("findEventsByTitle({}) call. No events found. Throwing error.", title);
		throw new EntryNotFoundException("Events not found.");
	}
	
	public List<Event> findEventsByDate(Date date) throws EntryNotFoundException {
		List<Event> resultList = eventDao.findEventsByDate(date);
		if(!resultList.isEmpty()) {
			logger.debug("findEventsByDate({}) call. Readressing to repository.", date);
			return resultList;
		}
		logger.error("findEventsByDate({}) call. No events found. Throwing error.", date);
		throw new EntryNotFoundException("Events not found.");
	}
	
	public Event updateEvent(Event event) throws EntryNotFoundException, EntryValidationException {
		validateEventFields(event);
		if (eventDao.findEventById(event.getId()) != null) {
			logger.debug("updateEvent({}) call. Readressing to repository.", event);
			return eventDao.updateEvent(event);
		}
		logger.error("updateEvent({}) call. No event found. Throwing error.", event);
		throw new EntryNotFoundException("Event not found.");
	}
	
	private void validateEventFields(Event event) throws EntryValidationException {
		if (event.getTitle() == null) {
			logger.error("validateEventFields({}) call. No title found. Throwing error.", event);
			throw new EntryValidationException("Title is mandatory.");
		}
		if (event.getDate() == null) {
			logger.error("validateEventFields({}) call. No date found. Throwing error.", event);
			throw new EntryValidationException("Date is mandatory.");
		}
	}
	
}
