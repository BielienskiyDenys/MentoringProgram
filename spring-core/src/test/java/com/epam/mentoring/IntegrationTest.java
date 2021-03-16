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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:beans.xml")
public class IntegrationTest {
    @Autowired
    private BookingFacade bookingFacade;

    @Test
    public void registered_user_books_ticket_for_valid_event() throws Exception {
        User user = bookingFacade.getUserById(333L);
        assertEquals("Peter", user.getName());

        Event event = bookingFacade.getEventById(10L);
        assertEquals("January Event", event.getTitle());

        Ticket ticket = bookingFacade.bookTicket(333L, 10L, 50, Ticket.Category.BAR );
        assertNotNull(ticket);
    }

    @Test
    public void existing_user_buys_ticket_twice() {
        Ticket userOneTicket = bookingFacade.bookTicket(333L, 10L, 50, Ticket.Category.BAR );
        assertEquals(333L, userOneTicket.getUserId());
        assertEquals(10L, userOneTicket.getEventId());

        Ticket userTwoTicket = bookingFacade.bookTicket(344L, 10L, 50, Ticket.Category.BAR );
        assertEquals(0, userTwoTicket.getUserId());
        assertEquals(0, userTwoTicket.getEventId());
    }

    @Test
    public void new_user_buys_ticket_for_non_existing_event() {
        User testUser = new UserImpl();
        testUser.setId(666L);
        testUser.setName("Thomas");
        testUser.setEmail("catch@mouse.com");

        User receivedUser = bookingFacade.createUser(testUser);
        assertEquals(testUser, receivedUser);

        Ticket ticket = bookingFacade.bookTicket(666L, 1000L, 50, Ticket.Category.BAR );
        assertEquals(0, ticket.getUserId());
        assertEquals(0, ticket.getEventId());
    }

    @Test
    public void new_user_changes_name_and_buys_ticket_for_new_event_and_cancels_it() throws Exception {
        User testUser = new UserImpl();
        testUser.setId(777L);
        testUser.setName("Terry");
        testUser.setEmail("flat@world.com");

        Event testEvent = new EventImpl();
        testEvent.setId(888L);
        testEvent.setTitle("Magic Conference");
        testEvent.setDate(Date.from(Instant.parse("2022-03-03T17:00:00.000Z")));

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

        assertTrue(newTicketsRequest.isEmpty());
    }


}
