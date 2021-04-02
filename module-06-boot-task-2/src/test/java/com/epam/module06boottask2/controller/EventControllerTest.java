package com.epam.module06boottask2.controller;

import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.entity.EventType;
import com.epam.module06boottask2.entity.Place;
import com.epam.module06boottask2.exception.EventNotFoundException;
import com.epam.module06boottask2.repository.EventRepo;
import com.epam.module06boottask2.service.EventService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventControllerTest {
    @Autowired
    private EventController eventController;

    @MockBean
    private EventService eventService;


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
    public void get_event_successful_scenario() {
        when(eventService.getEvent(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        Event receivedEvent = eventController.getEvent(TEST_EVENT_ID);
        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).getEvent(eq(TEST_EVENT_ID));
    }

    @Test
    public void create_event_successful_scenario() {
        when(eventService.createEvent(eq(testEvent))).thenReturn(testEvent);
        Event receivedEvent = eventController.createEvent(testEvent);
        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).createEvent(eq(testEvent));
    }

    @Test
    public void update_event_by_id_successful_scenario() {
        when(eventService.updateEvent(eq(testEvent), eq(TEST_EVENT_ID))).thenReturn(testEvent);
        Event receivedEvent = eventController.updateEvent(testEvent, TEST_EVENT_ID);
        assertEquals(TEST_EVENT_ID, receivedEvent.getId());
        assertEquals(testEvent.getTitle(), receivedEvent.getTitle());
        verify(eventService, times(1)).updateEvent(eq(testEvent), eq(TEST_EVENT_ID));
    }


    @Test
    public void delete_event_by_id_successful_scenario() {
        when(eventService.deleteEvent(eq(TEST_EVENT_ID))).thenReturn(testEvent);
        Event receivedEvent = eventController.deleteEvent(TEST_EVENT_ID);
        assertEquals(TEST_EVENT_ID, receivedEvent.getId());
        assertEquals(testEvent.getTitle(), receivedEvent.getTitle());
        verify(eventService, times(1)).deleteEvent(eq(TEST_EVENT_ID));
    }


    @Test
    public void get_events_by_title_with_title_scenario() {
        when(eventService.getAllEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(Arrays.asList(testEvent));
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(testEvent));
        List<Event> receivedEvents = eventController.getAllEventsByTitle(TEST_EVENT_TITLE);
        assertEquals(Arrays.asList(testEvent), receivedEvents);
        verify(eventService, times(1)).getAllEventsByTitle(eq(TEST_EVENT_TITLE));
        verify(eventService, times(0)).getAllEvents();
    }

    @Test
    public void get_events_by_title_without_title_scenario() {
        when(eventService.getAllEventsByTitle(eq(TEST_EVENT_TITLE))).thenReturn(Arrays.asList(testEvent));
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(testEvent));
        List<Event> receivedEvents = eventController.getAllEventsByTitle(null);
        assertEquals(Arrays.asList(testEvent), receivedEvents);
        verify(eventService, times(0)).getAllEventsByTitle(eq(TEST_EVENT_TITLE));
        verify(eventService, times(1)).getAllEvents();
    }
}
