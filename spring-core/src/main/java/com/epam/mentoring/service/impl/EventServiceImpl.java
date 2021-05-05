package com.epam.mentoring.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.mentoring.model.Event;
import com.epam.mentoring.repo.EventRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class EventServiceImpl implements EventService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Transactional
    public boolean addEvent(Event event) {
        if (validateEventFields(event) && !eventRepo.findById(event.getId()).isPresent()) {
            logger.debug("addEvent({}) call. Readressing to repository.", event);
            eventRepo.save(event);
            return true;
        }
        logger.error("addEvent({}) call. Event registered already.", event);
        return false;
    }

    @Transactional
    public boolean removeEventById(Long eventId) {
        Optional<Event> event = eventRepo.findById(eventId);
        if (event.isPresent()) {
            logger.debug("removeEventById({}) call. Readressing to repository.", eventId);
            eventRepo.deleteById(eventId);
            return true;
        }
        logger.error("removeEventById({}) call. No event found.", eventId);
        return false;
    }

    public Event findEventById(long eventId) {
        logger.debug("findEventById({}) call. Readressing to repository.", eventId);
        return eventRepo.findById(eventId).orElse(null);
    }

    public List<Event> findEventsByTitle(String title) {
        logger.debug("findEventsByTitle({}) call. Readressing to repository.", title);
        return eventRepo.findByTitleContaining(title);
    }

    public List<Event> findEventsByDate(Date date) {
        logger.debug("findEventsByDate({}) call. Readressing to repository.", date);
        return eventRepo.findByDate(date);
    }

    @Override
    @Transactional
    public boolean updateEvent(Event event) {
        if (validateEventFields(event) && eventRepo.findById(event.getId()).isPresent()) {
            logger.debug("updateEvent({}) call. Readressing to repository.", event);
            eventRepo.save(event);
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
