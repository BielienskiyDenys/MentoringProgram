package com.epam.module06boottask2.service;

import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.entity.EventType;
import com.epam.module06boottask2.entity.Place;
import com.epam.module06boottask2.exception.EventNotFoundException;
import com.epam.module06boottask2.repository.EventRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepo eventRepo;


    private final long TEST_EVENT_ID = 1L;
    private final long NEW_ID = 2L;
    private final String TEST_EVENT_TITLE = "Test Event Title";
    private final String TEST_EVENT_SPEAKER = "Test Event Speaker";
    private final Place TEST_EVENT_PLACE = Place.EARTH_HALL;
    private final EventType TEST_EVENT_TYPE = EventType.LECTION;
    private final Date TEST_EVENT_DATE = Date.from(Instant.parse("2022-10-10T20:00:00.000Z"));
    private Event testEvent;

    @BeforeEach
    public void init() {
        testEvent = new Event();
        testEvent.setId(TEST_EVENT_ID);
        testEvent.setEventType(TEST_EVENT_TYPE);
        testEvent.setDateTime(TEST_EVENT_DATE);
        testEvent.setPlace(TEST_EVENT_PLACE);
        testEvent.setTitle(TEST_EVENT_TITLE);
        testEvent.setSpeaker(TEST_EVENT_SPEAKER);
    }

    @Test
    public void get_event_by_id_successful_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));
        Event receivedEvent = eventService.getEvent(TEST_EVENT_ID);
        assertEquals(testEvent, receivedEvent);
        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
    }

    @Test
    public void get_event_by_id_not_found_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());
        Exception receivedException = null;
        try{
            Event receivedEvent = eventService.getEvent(TEST_EVENT_ID);
        } catch (EventNotFoundException ex) {
            receivedException = ex;
        }
        assertNotNull(receivedException);
        assertEquals(EventNotFoundException.class,receivedException.getClass());
        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
    }

    @Test
    public void create_event_successful_scenario() {
        when(eventRepo.save(eq(testEvent))).thenReturn(testEvent);
        Event receivedEvent = eventService.createEvent(testEvent);
        assertEquals(testEvent, receivedEvent);
        verify(eventRepo, times(1)).save(eq(testEvent));
    }

    @Test
    public void update_event_by_id_successful_scenario() {
        when(eventRepo.findById(eq(NEW_ID))).thenReturn(Optional.of(testEvent));
        when(eventRepo.save(eq(testEvent))).thenReturn(testEvent);
        Event receivedEvent = eventService.updateEvent(testEvent, NEW_ID);
        assertEquals(NEW_ID, receivedEvent.getId());
        assertEquals(testEvent.getTitle(), receivedEvent.getTitle());
        verify(eventRepo, times(1)).findById(eq(NEW_ID));
        verify(eventRepo, times(1)).save(eq(testEvent));
    }

    @Test
    public void update_event_by_id_not_found_scenario() {
        when(eventRepo.findById(eq(NEW_ID))).thenReturn(Optional.empty());
        when(eventRepo.save(eq(testEvent))).thenReturn(testEvent);
        Exception receivedException = null;
        try{
            Event receivedEvent = eventService.updateEvent(testEvent, NEW_ID);
        } catch (EventNotFoundException ex) {
            receivedException = ex;
        }
        assertNotNull(receivedException);
        assertEquals(EventNotFoundException.class,receivedException.getClass());
        verify(eventRepo, times(1)).findById(eq(NEW_ID));
        verify(eventRepo, times(0)).save(eq(testEvent));
    }

    @Test
    public void delete_event_by_id_successful_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.of(testEvent));
        doNothing().when(eventRepo).deleteById(eq(TEST_EVENT_ID));
        Event receivedEvent = eventService.deleteEvent(TEST_EVENT_ID);
        assertEquals(TEST_EVENT_ID, receivedEvent.getId());
        assertEquals(testEvent.getTitle(), receivedEvent.getTitle());
        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(1)).deleteById(eq(TEST_EVENT_ID));
    }

    @Test
    public void delete_event_by_id_not_found_scenario() {
        when(eventRepo.findById(eq(TEST_EVENT_ID))).thenReturn(Optional.empty());
        doNothing().when(eventRepo).deleteById(eq(TEST_EVENT_ID));
        Exception receivedException = null;
        try{
            Event receivedEvent = eventService.deleteEvent(TEST_EVENT_ID);
        } catch (EventNotFoundException ex) {
            receivedException = ex;
        }
        assertNotNull(receivedException);
        assertEquals(EventNotFoundException.class,receivedException.getClass());
        verify(eventRepo, times(1)).findById(eq(TEST_EVENT_ID));
        verify(eventRepo, times(0)).deleteById(eq(TEST_EVENT_ID));
    }

    @Test
    public void get_events_by_title_successful_scenario() {
        when(eventRepo.findAllByTitleContaining(eq(TEST_EVENT_TITLE))).thenReturn(Arrays.asList(testEvent));
        List<Event> receivedEvents = eventService.getAllEventsByTitle(TEST_EVENT_TITLE);
        assertEquals(Arrays.asList(testEvent), receivedEvents);
        verify(eventRepo, times(1)).findAllByTitleContaining(eq(TEST_EVENT_TITLE));
    }

    @Test
    public void get_all_events_successful_scenario() {
        when(eventRepo.findAll()).thenReturn(Arrays.asList(testEvent));
        List<Event> receivedEvents = eventService.getAllEvents();
        assertEquals(Arrays.asList(testEvent), receivedEvents);
        verify(eventRepo, times(1)).findAll();
    }
}
