package com.epam.mentoring.facade.impl;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.service.EventService;
import com.epam.mentoring.service.TicketService;
import com.epam.mentoring.service.UserService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:beans.xml")
class BookingFacadeImplTest {
    @Autowired
    private BookingFacade bookingFacade;

    @MockBean
    UserService userService;

    @MockBean
    EventService eventService;

    @MockBean
    TicketService ticketService;

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
    void getEventById_successful_scenario() {
        when(eventService.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        Event receivedEvent = bookingFacade.getEventById(TEST_EVENT_ID);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).findEventById(eq(TEST_EVENT_ID));
    }


    @Test
    void getEventById_not_found() {
        Event eventFromService = new EventImpl();
        eventFromService.setTitle("No such event");
        when(eventService.findEventById(eq(TEST_EVENT_ID))).thenReturn(eventFromService);

        Event receivedEvent = bookingFacade.getEventById(TEST_EVENT_ID);

        assertNotNull(receivedEvent);
        assertEquals("No such event", receivedEvent.getTitle());
        verify(eventService, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void getEventsByTitle_successful_scenario() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new EventImpl());
        eventList.add(testEvent);
        when(eventService.findEventsByTitle(eq("Event"))).thenReturn(eventList);

        List<Event> receivedEvents = bookingFacade.getEventsByTitle("Event", 10, 0);

        assertEquals(2, receivedEvents.size());
        verify(eventService, times(1)).findEventsByTitle(eq("Event"));
    }


    @Test
    void getEventsByTitle_not_found() {
        when(eventService.findEventsByTitle(eq("Event"))).thenReturn(Collections.emptyList());

        List<Event> receivedEvents = bookingFacade.getEventsByTitle("Event", 10, 0);

        assertTrue(receivedEvents.isEmpty());
        verify(eventService, times(1)).findEventsByTitle(eq("Event"));
    }

    @Test
    void getEventsForDay_successful_scenario() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new EventImpl());
        eventList.add(testEvent);
        when(eventService.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(eventList);

        List<Event> receivedEvents = bookingFacade.getEventsForDay(TEST_EVENT_DATE, 10, 0);

        assertEquals(2, receivedEvents.size());
        verify(eventService, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
    }


    @Test
    void getEventsForDay_not_found() {
        when(eventService.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(Collections.emptyList());

        List<Event> receivedEvents = bookingFacade.getEventsForDay(TEST_EVENT_DATE, 10, 0);

        assertTrue(receivedEvents.isEmpty());
        verify(eventService, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
    }

    @Test
    void createEvent_successful_scenario()  {
        when(eventService.addEvent(any(Event.class))).thenReturn(true);

        Event receivedEvent = bookingFacade.createEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void createEvent_validation_exception() {
        when(eventService.addEvent(any(Event.class))).thenReturn(false);

        Event receivedEvent = bookingFacade.createEvent(testEvent);

        assertEquals("Failed to create", receivedEvent.getTitle());
        verify(eventService, times(1)).addEvent(eq(testEvent));
    }


    @Test
    void updateEvent_successful_scenario() {
        when(eventService.updateEvent(any(Event.class))).thenReturn(true);

        Event receivedEvent = bookingFacade.updateEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).updateEvent(eq(testEvent));
    }

    @Test
    void deleteEvent_successful_scenario() {
        when(eventService.removeEventById(anyLong())).thenReturn(true);

        boolean wasDeleted = bookingFacade.deleteEvent(TEST_EVENT_ID);

        assertTrue(wasDeleted);
        verify(eventService, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void deleteEvent_not_found() {
        when(eventService.removeEventById(anyLong())).thenReturn(false);

        boolean wasDeleted = bookingFacade.deleteEvent(TEST_EVENT_ID);

        assertFalse(wasDeleted);
        verify(eventService, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }


}
