package com.epam.mentoring.service.impl;

import com.epam.mentoring.dao.EventDao;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:beans.xml")
class EventServiceImplTest {
    @Autowired
    private EventService eventService;

    @MockBean
    private EventDao eventDao;

    private Event testEvent = new EventImpl();
    private final long TEST_EVENT_ID = 88888L;
    private final String TEST_EVENT_TITLE = "testEventTitle";
    private final Date TEST_EVENT_DATE = Date.from(Instant.parse("2030-06-01T18:30:00.000Z"));

    @BeforeEach
    private void init() {
        testEvent.setId(TEST_EVENT_ID);
        testEvent.setTitle(TEST_EVENT_TITLE);
        testEvent.setDate(TEST_EVENT_DATE);
    }

    @Test
    void addEvent_successful_scenario() {
        doNothing().when(eventDao).addEvent(any(Event.class));
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);

        eventService.addEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void addEvent_registered_already() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        boolean eventCreated = eventService.addEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).addEvent(any(Event.class));
        assertFalse(eventCreated);
    }

    @Test
    void addEvent_verification_exception_thrown() {
        testEvent.setDate(null);

        eventService.addEvent(testEvent);

        verify(eventDao, times(1)).findEventById(anyLong());
        verify(eventDao, times(1)).addEvent(any(Event.class));
    }

    @Test
    void removeEvent_successful_scenario() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        when(eventDao.removeEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        eventService.removeEventById(TEST_EVENT_ID);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void removeEvent_not_failure() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);

        boolean eventDeleted = eventService.removeEventById(TEST_EVENT_ID);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).removeEventById(anyLong());
        assertFalse(eventDeleted);
    }

    @Test
    void findEventById_successful_scenario() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        eventService.findEventById(TEST_EVENT_ID);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void findEventById_not_found_exception() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);

         Event event = eventService.findEventById(TEST_EVENT_ID);

        assertNotNull(event);
        assertEquals("No such event", event.getTitle());
        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void findEventsByTitle_successful_scenario() {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventDao.findEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByTitle(TEST_EVENT_TITLE);

        verify(eventDao, times(1)).findEventsByTitle(eq(TEST_EVENT_TITLE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByTitle_no_events() {
        when(eventDao.findEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(Collections.emptyList());

        List<Event> receivedList = eventService.findEventsByTitle(TEST_EVENT_TITLE);

        verify(eventDao, times(1)).findEventsByTitle(eq(TEST_EVENT_TITLE));
        assertTrue(receivedList.isEmpty());
    }

    @Test
    void findEventsByDate_successful_scenario() {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventDao.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByDate(TEST_EVENT_DATE);

        verify(eventDao, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByDate_no_events() {
        when(eventDao.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(Collections.emptyList());

        List<Event> receivedList = eventService.findEventsByDate(TEST_EVENT_DATE);

        verify(eventDao, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
        assertTrue(receivedList.isEmpty());
    }

    @Test
    void updateEvent_successful_scenario() {
        testEvent.setTitle("newTitle");
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        when(eventDao.updateEvent(eq(testEvent))).thenReturn(testEvent);

        boolean eventReceived  = eventService.updateEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).updateEvent(eq(testEvent));
        assertTrue(eventReceived);
    }

    @Test
    void updateEvent_not_found_exception() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);

        boolean eventReceived = eventService.updateEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).updateEvent(any(Event.class));
        assertFalse(eventReceived);
    }

    @Test
    void updateEvent_validation_exception() {
        testEvent.setTitle("Ti");

        boolean eventReceived = eventService.updateEvent(testEvent);

        verify(eventDao, times(0)).findEventById(anyLong());
        verify(eventDao, times(0)).updateEvent(any(Event.class));
        assertFalse(eventReceived);
    }
}
