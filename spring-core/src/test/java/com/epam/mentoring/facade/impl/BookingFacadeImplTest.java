package com.epam.mentoring.facade.impl;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
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
import java.util.*;

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
    void getEventById_successful_scenario() throws EntryNotFoundException {
        when(eventService.findEventById(eq(TEST_EVENT_ID))).thenReturn(testEvent);

        Event receivedEvent = bookingFacade.getEventById(TEST_EVENT_ID);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).findEventById(eq(TEST_EVENT_ID));
    }


    @Test
    void getEventById_not_found() throws EntryNotFoundException {
        when(eventService.findEventById(eq(TEST_EVENT_ID))).thenThrow(new EntryNotFoundException());

        Event receivedEvent = bookingFacade.getEventById(TEST_EVENT_ID);

        assertNull(receivedEvent);
        verify(eventService, times(1)).findEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void getEventsByTitle_successful_scenario() throws EntryNotFoundException {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new EventImpl());
        eventList.add(testEvent);
        when(eventService.findEventsByTitle(eq("Event"))).thenReturn(eventList);

        List<Event> receivedEvents = bookingFacade.getEventsByTitle("Event", 10, 0);

        assertEquals(2, receivedEvents.size());
        verify(eventService, times(1)).findEventsByTitle(eq("Event"));
    }


    @Test
    void getEventsByTitle_not_found() throws EntryNotFoundException {
        when(eventService.findEventsByTitle(eq("Event"))).thenThrow(new EntryNotFoundException());;

        List<Event> receivedEvents = bookingFacade.getEventsByTitle("Event", 10, 0);

        assertTrue(receivedEvents.isEmpty());
        verify(eventService, times(1)).findEventsByTitle(eq("Event"));
    }

    @Test
    void getEventsForDay_successful_scenario() throws EntryNotFoundException {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new EventImpl());
        eventList.add(testEvent);
        when(eventService.findEventsByDate(eq(TEST_EVENT_DATE))).thenReturn(eventList);

        List<Event> receivedEvents = bookingFacade.getEventsForDay(TEST_EVENT_DATE, 10, 0);

        assertEquals(2, receivedEvents.size());
        verify(eventService, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
    }


    @Test
    void getEventsForDay_not_found() throws EntryNotFoundException {
        when(eventService.findEventsByDate(eq(TEST_EVENT_DATE))).thenThrow(new EntryNotFoundException());;

        List<Event> receivedEvents = bookingFacade.getEventsForDay(TEST_EVENT_DATE, 10, 0);

        assertTrue(receivedEvents.isEmpty());
        verify(eventService, times(1)).findEventsByDate(eq(TEST_EVENT_DATE));
    }

    @Test
    void createEvent_successful_scenario() throws EntryValidationException, EntryExistsAlreadyException {
        doNothing().when(eventService).addEvent(any(Event.class));

        Event receivedEvent = bookingFacade.createEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void createEvent_validation_exception() throws EntryValidationException, EntryExistsAlreadyException {
        doThrow(new EntryValidationException()).when(eventService).addEvent(any(Event.class));

        Event receivedEvent = bookingFacade.createEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void createEvent_already_exists_exception() throws EntryValidationException, EntryExistsAlreadyException {
        doThrow(new EntryExistsAlreadyException()).when(eventService).addEvent(any(Event.class));

        Event receivedEvent = bookingFacade.createEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).addEvent(eq(testEvent));
    }

    @Test
    void updateEvent_successful_scenario() throws EntryValidationException, EntryNotFoundException {
        when(eventService.updateEvent(any(Event.class))).thenReturn(testEvent);

        Event receivedEvent = bookingFacade.updateEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).updateEvent(eq(testEvent));
    }

    @Test
    void updateEvent_validation_exception() throws EntryValidationException, EntryNotFoundException {
        when(eventService.updateEvent(any(Event.class))).thenThrow(new EntryValidationException());

        Event receivedEvent = bookingFacade.updateEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).updateEvent(eq(testEvent));
    }

    @Test
    void updateEvent_not_found_exception() throws EntryValidationException, EntryNotFoundException {
        when(eventService.updateEvent(any(Event.class))).thenThrow(new EntryNotFoundException());

        Event receivedEvent = bookingFacade.updateEvent(testEvent);

        assertEquals(testEvent, receivedEvent);
        verify(eventService, times(1)).updateEvent(eq(testEvent));
    }

    @Test
    void deleteEvent_successful_scenario() throws EntryNotFoundException {
        when(eventService.removeEventById(anyLong())).thenReturn(testEvent);

        boolean wasDeleted = bookingFacade.deleteEvent(TEST_EVENT_ID);

        assertTrue(wasDeleted);
        verify(eventService, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }

    @Test
    void deleteEvent_not_found_exception() throws EntryNotFoundException {
        when(eventService.removeEventById(anyLong())).thenThrow(new EntryNotFoundException());

        boolean wasDeleted = bookingFacade.deleteEvent(TEST_EVENT_ID);

        assertFalse(wasDeleted);
        verify(eventService, times(1)).removeEventById(eq(TEST_EVENT_ID));
    }


}
