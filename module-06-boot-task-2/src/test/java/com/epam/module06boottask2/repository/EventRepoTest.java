package com.epam.module06boottask2.repository;

import com.epam.module06boottask2.H2TestProfileJPAConfig;
import com.epam.module06boottask2.Module06BootTask2Application;
import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.entity.EventType;
import com.epam.module06boottask2.entity.Place;
import com.epam.module06boottask2.repository.EventRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        Module06BootTask2Application.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
class EventRepoTest {
    @Autowired
    private EventRepo eventRepo;

    Event testEvent;

    @BeforeEach
    public void init() {
        testEvent = new Event();
        testEvent.setTitle("NEW Test Event");
        testEvent.setSpeaker("Test Speaker");
        testEvent.setEventType(EventType.LECTION);
        testEvent.setPlace(Place.EARTH_HALL);
        testEvent.setDateTime(Date.from(Instant.parse("2022-10-10T20:00:00.000Z")));
    }

    @Test
    public void shouldPutEventToDB() {
        Event receivedEvent = eventRepo.save(testEvent);
        assertEquals(testEvent.getSpeaker(), receivedEvent.getSpeaker());
    }

    @Test
    public void shouldGetEventFromDB() {
        Event receivedEvent = eventRepo.findById(1L).orElseGet(() -> testEvent);
        assertEquals("Test event 01", receivedEvent.getTitle());
    }

    @Test
    public void shouldGetAllEventsFromDB() {
        List<Event> eventList = eventRepo.findAll();
        assertEquals(4, eventList.size());
    }

    @Test
    public void shouldGetEventsByTitleFromDB() {
        List<Event> eventList = eventRepo.findAllByTitleContaining("marker");
        assertEquals(2, eventList.size());
    }
}
