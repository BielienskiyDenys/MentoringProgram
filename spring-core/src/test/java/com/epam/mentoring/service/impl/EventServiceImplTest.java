package com.epam.mentoring.service.impl;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.service.EventService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
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
    private final Date TEST_EVENT_DATE = Date.from(Instant.parse("2020-06-01T18:30:00.000Z"));
    @BeforeEach
    private void init() {
        testEvent.setId(TEST_EVENT_ID);
        testEvent.setTitle(TEST_EVENT_TITLE);
        testEvent.setDate(TEST_EVENT_DATE);
    }

    @Test
    void addEvent_successful_scenario() throws EntryValidationException, EntryExistsAlreadyException {
        doNothing().when(eventDao).addEvent(any(Event.class));
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);

        eventService.addEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void addEvent_registered_already_exception_thrown() throws EntryValidationException {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        EntryExistsAlreadyException receivedException = null;

        try {
            eventService.addEvent(testEvent);
        } catch (EntryExistsAlreadyException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Event already registered.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).addEvent(any(Event.class));
    }

    @Test
    void addEvent_verification_exception_thrown() throws EntryExistsAlreadyException {
        testEvent.setDate(null);
        EntryValidationException receivedException = null;

        try {
            eventService.addEvent(testEvent);
        } catch (EntryValidationException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Date is mandatory.", receivedException.getMessage());
        verify(eventDao, times(0)).findEventById(anyLong());
        verify(eventDao, times(0)).addEvent(any(Event.class));
    }

    @Test
    void removeEvent_successful_scenario() throws EntryNotFoundException {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        when(eventDao.removeEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        eventService.removeEventById(TEST_EVENT_ID);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void removeEvent_not_found_exception() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);
        EntryNotFoundException receivedException = null;

        try {
            eventService.removeEventById(TEST_EVENT_ID);
        } catch (EntryNotFoundException e) {
           receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Event not found.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).removeEventById(anyLong());
    }

    @Test
    void findEventById_successful_scenario() throws EntryNotFoundException {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        eventService.findEventById(TEST_EVENT_ID);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void findEventById_not_found_exception() {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);
        EntryNotFoundException receivedException = null;

        try {
            eventService.findEventById(TEST_EVENT_ID);
        } catch (EntryNotFoundException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Event not found.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void findEventsByTitle_successful_scenario() throws EntryNotFoundException {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventDao.findEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByTitle(TEST_EVENT_TITLE);

        verify(eventDao, times(1)).findEventsByTitle(eq(TEST_EVENT_TITLE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByTitle_not_found_exception() {
        when(eventDao.findEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(Collections.emptyList());
        EntryNotFoundException receivedException = null;

        try {
            eventService.findEventsByTitle(TEST_EVENT_TITLE);
        } catch (EntryNotFoundException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Events not found.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventsByTitle(eq(TEST_EVENT_TITLE));
    }

    @Test
    void findEventsByDate_successful_scenario() throws EntryNotFoundException {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventDao.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByDate(TEST_EVENT_DATE);

        verify(eventDao, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByDate_not_found_exception() {
        when(eventDao.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(Collections.emptyList());
        EntryNotFoundException receivedException = null;

        try {
            eventService.findEventsByDate(TEST_EVENT_DATE);
        } catch (EntryNotFoundException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Events not found.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
    }

    @Test
    void updateEvent_successful_scenario() throws EntryValidationException, EntryNotFoundException {
        testEvent.setTitle("newTitle");
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        when(eventDao.updateEvent(eq(testEvent))).thenReturn(testEvent);

        Event receivedEvent = eventService.updateEvent(testEvent);

        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(1)).updateEvent(eq(testEvent));
        assertEquals("newTitle", receivedEvent.getTitle());
    }

    @Test
    void updateEvent_not_found_exception() throws EntryValidationException {
        when(eventDao.findEventById(eq(TEST_EVENT_ID))).thenReturn(null);
        EntryNotFoundException receivedException = null;

        Event receivedEvent = null;
        try {
            receivedEvent = eventService.updateEvent(testEvent);
        } catch (EntryNotFoundException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Event not found.", receivedException.getMessage());
        verify(eventDao, times(1)).findEventById(eq(TEST_EVENT_ID));
        verify(eventDao, times(0)).updateEvent(any(Event.class));
    }

    @Test
    void updateEvent_validation_exception() throws  EntryNotFoundException {
        testEvent.setDate(null);
        EntryValidationException receivedException = null;

        try {
            eventService.updateEvent(testEvent);
        } catch (EntryValidationException e) {
            receivedException = e;
        }

        assertNotNull(receivedException);
        assertEquals("Date is mandatory.", receivedException.getMessage());
        verify(eventDao, times(0)).findEventById(anyLong());
        verify(eventDao, times(0)).updateEvent(any(Event.class));
    }
}
