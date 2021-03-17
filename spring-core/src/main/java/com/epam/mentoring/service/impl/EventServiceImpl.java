package com.epam.mentoring.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.epam.mentoring.model.impl.EventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.service.EventService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class EventServiceImpl implements EventService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private EventDao eventDao;

    public EventDao getEventDao() {
        return eventDao;
    }
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public boolean addEvent(Event event) {
        if (validateEventFields(event) && eventDao.findEventById(event.getId()) == null) {
            logger.debug("addEvent({}) call. Readressing to repository.", event);
            eventDao.addEvent(event);
            return true;
        }
        logger.error("addEvent({}) call. Event registered already.", event);
        return false;
    }

    public boolean removeEventById(Long eventId) {
        Event event = eventDao.findEventById(eventId);
        if (event != null) {
            logger.debug("removeEventById({}) call. Readressing to repository.", eventId);
            eventDao.removeEventById(eventId);
            return true;
        }
        logger.error("removeEventById({}) call. No event found.", eventId);
        return false;
    }

    public Event findEventById(long eventId) {
        logger.debug("findEventById({}) call. Readressing to repository.", eventId);
        return eventDao.findEventById(eventId);
    }

    public List<Event> findEventsByTitle(String title) {
        logger.debug("findEventsByTitle({}) call. Readressing to repository.", title);
        return eventDao.findEventsByTitle(title);
    }

    public List<Event> findEventsByDate(Date date) {
        logger.debug("findEventsByDate({}) call. Readressing to repository.", date);
        return eventDao.findEventsByDate(date);
    }

    public boolean updateEvent(Event event) {
        if (validateEventFields(event) && eventDao.findEventById(event.getId()) != null) {
            logger.debug("updateEvent({}) call. Readressing to repository.", event);
            eventDao.updateEvent(event);
            return true;
        }
        logger.error("updateEvent({}) call. Event not updated. Invalid fields or no such event in storage.", event);
        return false;
    }

    private boolean validateEventFields(Event event) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        for (ConstraintViolation<Event> violation : violations) {
            logger.error(violation.getMessage());
        }
        return violations.isEmpty();
    }

}
