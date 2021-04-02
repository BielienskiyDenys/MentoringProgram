package com.epam.module06boottask2.controller;

import com.epam.module06boottask2.Module06BootTask2Application;
import com.epam.module06boottask2.entity.Event;
import com.epam.module06boottask2.entity.EventType;
import com.epam.module06boottask2.entity.Place;
import com.epam.module06boottask2.service.EventService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EventControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;


    private final long TEST_EVENT_ID = 1L;
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
    public void get_event_by_id_should_call_corresponding_methods() throws Exception {
        when(eventService.getEvent(TEST_EVENT_ID)).thenReturn(testEvent);
        mockMvc.perform(get("/events/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_EVENT_TITLE)));
    }
}
