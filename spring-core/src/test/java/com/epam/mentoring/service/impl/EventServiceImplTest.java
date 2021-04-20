package com.epam.mentoring.service.impl;

import com.epam.mentoring.model.Event;
import com.epam.mentoring.repo.EventRepo;
import com.epam.mentoring.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class EventServiceImplTest {
    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepo eventRepo;

    private Event testEvent = new Event();
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
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());

        boolean result = eventService.addEvent(testEvent);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(1)).save(eq(testEvent));
        assertTrue(result);
    }

    @Test
    void addEvent_registered_already() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));

        boolean eventCreated = eventService.addEvent(testEvent);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(0)).save(any(Event.class));
        assertFalse(eventCreated);
    }

    @Test
    void addEvent_verification_exception_thrown() {
        testEvent.setDate(null);

        boolean result = eventService.addEvent(testEvent);

        verify(eventRepo, times(0)).findById(anyLong());
        verify(eventRepo, times(0)).save(any(Event.class));
        assertFalse(result);
    }

    @Test
    void removeEvent_successful_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));


        boolean result = eventService.removeEventById(TEST_EVENT_ID);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(1)).deleteById(eq(TEST_EVENT_ID));
        assertTrue(result);
    }

    @Test
    void removeEvent_not_failure() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());

        boolean eventDeleted = eventService.removeEventById(TEST_EVENT_ID);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(0)).deleteById(anyLong());
        assertFalse(eventDeleted);
    }

    @Test
    void findEventById_successful_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));

        Event event = eventService.findEventById(TEST_EVENT_ID);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        assertEquals(testEvent, event);
    }

    @Test
    void findEventById_not_found_exception() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());

         Event event = eventService.findEventById(TEST_EVENT_ID);

        assertNull(event);
        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
    }

    @Test
    void findEventsByTitle_successful_scenario() {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventRepo.findByTitleContaining(eq(TEST_EVENT_TITLE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByTitle(TEST_EVENT_TITLE);

        verify(eventRepo, times(1)).findByTitleContaining(eq(TEST_EVENT_TITLE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByTitle_no_events() {
        when(eventRepo.findByTitleContaining(eq(TEST_EVENT_TITLE))).thenReturn(Collections.emptyList());

        List<Event> receivedList = eventService.findEventsByTitle(TEST_EVENT_TITLE);

        verify(eventRepo, times(1)).findByTitleContaining(eq(TEST_EVENT_TITLE));
        assertTrue(receivedList.isEmpty());
    }

    @Test
    void findEventsByDate_successful_scenario() {
        List<Event> testEventLists = new ArrayList<>();
        testEventLists.add(testEvent);
        when(eventRepo.findByDate(eq(TEST_EVENT_DATE))).thenReturn(testEventLists);

        List<Event> receivedList = eventService.findEventsByDate(TEST_EVENT_DATE);

        verify(eventRepo, times(1)).findByDate(eq(TEST_EVENT_DATE));
        assertEquals(1, receivedList.size());
        assertEquals(testEvent, receivedList.get(0));
    }

    @Test
    void findEventsByDate_no_events() {
        when(eventRepo.findByDate(eq(TEST_EVENT_DATE))).thenReturn(Collections.emptyList());

        List<Event> receivedList = eventService.findEventsByDate(TEST_EVENT_DATE);

        verify(eventRepo, times(1)).findByDate(eq(TEST_EVENT_DATE));
        assertTrue(receivedList.isEmpty());
    }

    @Test
    void updateEvent_successful_scenario() {
        testEvent.setTitle("newTitle");
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));
        when(eventRepo.save(eq(testEvent))).thenReturn(testEvent);

        boolean eventReceived  = eventService.updateEvent(testEvent);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(1)).save(eq(testEvent));
        assertTrue(eventReceived);
    }

    @Test
    void updateEvent_not_found_exception() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());

        boolean eventReceived = eventService.updateEvent(testEvent);

        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(0)).save(any(Event.class));
        assertFalse(eventReceived);
    }

    @Test
    void updateEvent_validation_exception() {
        testEvent.setTitle("Ti");

        boolean eventReceived = eventService.updateEvent(testEvent);

        verify(eventRepo, times(0)).findById(anyLong());
        verify(eventRepo, times(0)).save(any(Event.class));
        assertFalse(eventReceived);
    }
}
