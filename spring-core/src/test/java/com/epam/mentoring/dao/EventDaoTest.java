package com.epam.mentoring.dao;

import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:beans.xml")
class EventDaoTest {
    @Autowired
    private EventDao eventDao;

    private Event testEvent = new EventImpl();
    private Event initialEvent = new EventImpl();
    private final long TEST_EVENT_ID = 88888L;
    private final String TEST_EVENT_TITLE = "testEventTitle";
    private final Date TEST_EVENT_DATE = Date.from(Instant.parse("2020-06-01T18:30:00.000Z"));
    @BeforeEach
    private void init() {
        testEvent.setId(TEST_EVENT_ID);
        testEvent.setTitle(TEST_EVENT_TITLE);
        testEvent.setDate(TEST_EVENT_DATE);

        initialEvent.setId(77777L);
        initialEvent.setTitle("initialEvent");
        initialEvent.setDate(Date.from(Instant.parse("2020-11-11T18:30:00.000Z")));

        Map<Long, Event> testMap = new HashMap<>();
        testMap.put(initialEvent.getId(), initialEvent);
        eventDao.setEvents(testMap);
    }

    @Test
    void addEvent_successful_scenario() {
        eventDao.addEvent(testEvent);

        assertEquals(2, eventDao.getEvents().size());
        assertTrue(eventDao.getEvents().containsValue(testEvent));
    }

    @Test
    void removeEvent_successful_scenario() {
        assertTrue(eventDao.getEvents().containsValue(initialEvent));

        eventDao.removeEventById(77777L);

        assertEquals(0, eventDao.getEvents().size());
        assertFalse(eventDao.getEvents().containsValue(initialEvent));
    }


    @Test
    void findEventById_successful_scenario() {
        Event receivedEvent = eventDao.findEventById(77777L);

        assertEquals(initialEvent, receivedEvent);
    }

    @Test
    void updateEvent_successful_scenario() {
        assertEquals("initialEvent", eventDao.getEvents().get(77777L).getTitle());
        testEvent.setId(77777L);

        Event receivedEvent = eventDao.updateEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        assertEquals(TEST_EVENT_TITLE, eventDao.getEvents().get(77777L).getTitle());
    }

    @Test
    void findEventsByTitle_successful_scenario() {
        eventDao.getEvents().put(testEvent.getId(), testEvent);

        List<Event> receivedEvents = eventDao.findEventsByTitle("Event");

        assertEquals(2, receivedEvents.size());
        assertTrue(receivedEvents.contains(testEvent));
        assertTrue(receivedEvents.contains(initialEvent));
    }

    @Test
    void findEventsByDate_successful_scenario() {
        eventDao.getEvents().put(testEvent.getId(), testEvent);
        Date dateToSearch = Date.from(Instant.parse("2020-11-11T20:30:00.000Z"));

        List<Event> receivedEvents = eventDao.findEventsByDate(dateToSearch);

        assertEquals(1, receivedEvents.size());
        assertTrue(receivedEvents.contains(initialEvent));
    }


}
