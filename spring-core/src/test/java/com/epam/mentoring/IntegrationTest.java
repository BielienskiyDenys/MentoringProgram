package com.epam.mentoring;

import com.epam.mentoring.model.*;
import com.epam.mentoring.repo.AccountRepo;
import com.epam.mentoring.repo.EventRepo;
import com.epam.mentoring.repo.TicketRepo;
import com.epam.mentoring.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepo eventRepo;
    @MockBean
    private AccountRepo accountRepo;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private TicketRepo ticketRepo;

    private Event testEvent = new Event();
    private final long TEST_EVENT_ID = 88888L;
    private final String TEST_EVENT_TITLE = "testEventTitle";
    private final Date TEST_EVENT_DATE = Date.from(Instant.parse("2030-06-01T18:30:00.000Z"));
    private final Double TEST_EVENT_PRICE = 500.0;
    private final long TEST_USER_ID = 33333L;
    private final String TEST_USER_NAME = "testUser";
    private final String TEST_USER_EMAIL = "test@email.com";
    private final User testUser = new User(TEST_USER_ID, TEST_USER_NAME, TEST_USER_EMAIL);
    private UserAccount testUserAccount = new UserAccount(testUser);
    private Ticket testTicket = new Ticket();
    private final long TEST_TICKET_ID = 44444L;
    private final int TEST_TICKET_PLACE = 50;
    private final Category TEST_TICKET_CATEGORY = Category.STANDARD;

    @BeforeEach
    private void init() {
        testEvent.setId(TEST_EVENT_ID);
        testEvent.setTitle(TEST_EVENT_TITLE);
        testEvent.setDate(TEST_EVENT_DATE);
        testEvent.setTicketPrice(TEST_EVENT_PRICE);
        testUserAccount.setBalance(1000.00);
        testTicket.setId(TEST_TICKET_ID);
        testTicket.setEventId(TEST_EVENT_ID);
        testTicket.setUserId(TEST_USER_ID);
        testTicket.setPlace(TEST_TICKET_PLACE);
        testTicket.setCategory(TEST_TICKET_CATEGORY);
    }

    @Test
    public void successful_find_event_by_id_scenario() throws Exception {
        when(eventRepo.findById(anyLong())).thenReturn(Optional.of(testEvent));
        this.mockMvc.perform(get("/events/?event-id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEventTitle")));
    }

    @Test
    public void failed_find_event_by_id_scenario() throws Exception {
        when(eventRepo.findById(anyLong())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/events/?event-id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("No events Available")));
    }


    @Test
    public void successful_booking_ticket_scenario() throws Exception {
        when(eventRepo.findById(anyLong())).thenReturn(Optional.of(testEvent));
        when(accountRepo.findById(anyLong())).thenReturn(Optional.of(testUserAccount));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(ticketRepo.save(any(Ticket.class))).thenReturn(testTicket);
        this.mockMvc.perform(
                post("/tickets")
                        .param("user-id", "33333")
                        .param("event-id", "88888")
                        .param("place", "50")
                        .param("category-id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully booked ticket")))
                .andExpect(content().string(containsString("44444")));
    }

    @Test
    public void findEventByTitleShouldReturnThreeEventsFromPreLoadedBean() throws Exception {
        this.mockMvc.perform(get("/events/title?title=Event&page-size=10&page-num=0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("January Event")))
                .andExpect(content().string(containsString("March Event")))
                .andExpect(content().string(containsString("October Event")));
    }

    @Test
    public void findEventByDayShouldReturnEventFromPreLoadedBean() throws Exception {
        this.mockMvc.perform(get("/events/day?day=2022-01-01&page-size=10&page-num=0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("January Event")));
    }
}
