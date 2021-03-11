package com.epam.mentoring;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.model.impl.UserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:beans.xml")
public class IntegrationTest {
    @Autowired
    private BookingFacade bookingFacade;

    @Test
    public void test() throws Exception {
        User testUser = new UserImpl();
        testUser.setId(777L);
        testUser.setName("Terry");
        testUser.setEmail("flat@world.com");

        Event testEvent = new EventImpl();
        testEvent.setId(888L);
        testEvent.setTitle("Magic Conference");
        testEvent.setDate(Date.from(Instant.parse("2021-03-03T17:00:00.000Z")));

        User receivedUser = bookingFacade.createUser(testUser);
        assertEquals(testUser, receivedUser);

        testUser.setName("Terry Pratchett");
        receivedUser = bookingFacade.updateUser(testUser);
        assertEquals(testUser, receivedUser);

        receivedUser = bookingFacade.getUserById(777l);
        assertEquals(testUser, receivedUser);

        bookingFacade.createEvent(testEvent);

        Event receivedEvent = bookingFacade.getEventsByTitle("Magic", 10, 0).get(0);
        assertEquals(testEvent, receivedEvent);

        bookingFacade.bookTicket(777L, 888L, 15, Ticket.Category.PREMIUM);

        Ticket ticket = bookingFacade.getBookedTickets(testUser, 10, 0).get(0);

        assertEquals(777L, ticket.getUserId());
        assertEquals(888L, ticket.getEventId());
        assertEquals(Ticket.Category.PREMIUM, ticket.getCategory());
        assertEquals(15, ticket.getPlace());

        long ticketId = ticket.getId();

        bookingFacade.cancelTicket(ticketId);

        List<Ticket> newTicketsRequest = bookingFacade.getBookedTickets(testUser, 10, 0);

        assertNull(newTicketsRequest);
    }
}
