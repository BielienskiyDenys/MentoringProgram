package com.epam.mentoring.facade.impl;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.model.impl.UserImpl;
import com.epam.mentoring.service.EventService;
import com.epam.mentoring.service.TicketService;
import com.epam.mentoring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookingFacadeImpl implements BookingFacade {
    private static Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);
    UserService userService;
    EventService eventService;
    TicketService ticketService;

    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @Override
    public Event getEventById(long eventId) {
        return eventService.findEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> events = eventService.findEventsByTitle(title);
        if (events.isEmpty()) {
            logger.error("getEventsByTitle({}) call. No events found.", title);
            return events;
        }
        logger.debug("getEventsByTitle({}) call. Events found.", title);
        return events.stream().skip((long) pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> events = eventService.findEventsByDate(day);
        if (events.isEmpty()) {
            logger.error("getEventsForDay({}) call. No events found.", day);
            return events;
        }
        logger.debug("getEventsForDay({}) call. Events found.", day);
        return events.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        if (eventService.addEvent(event)) {
            logger.debug("createEvent({}) call. Event created.", event);
            return event;
        }
        Event newEvent = new EventImpl();
        logger.error("createEvent({}) call. Failed to create.", event);
        newEvent.setTitle("Failed to create");
        return newEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        if(!eventService.updateEvent(event)) {
            return null;
        };
        logger.debug("updateEvent({}) call.", event);
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        logger.debug("deleteEvent({}) call.", eventId);
        return eventService.removeEventById(eventId);
    }


    @Override
    public User getUserById(long userId) {
        return userService.findUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> users = userService.findUsersByName(name);
        if (users.isEmpty()) {
            logger.error("getUserByName({}) call. No user found.", name);
            return users;
        }
            logger.debug("getUserByName({}) call. User found.", name);
        return users.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        if( userService.addUser(user)) {
            logger.debug("createUser({}) call. User created.", user);
            return user;
        }
        logger.error("createUser({}) call. Failed to create user.", user);
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (userService.updateUser(user)) {
            logger.debug("updateUser({}) call. User updated.", user);
            return user;
        }
        logger.error("updateUser({}) call. Failed to update user.", user);
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        if (userService.removeUserById(userId)) {
            logger.debug("deleteUser({}) call. User deleted.", userId);
            return true;
        }
        logger.debug("deleteUser({}) call. Failed to delete user.", userId);
        return false;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Category category) {
        return ticketService.addTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> tickets = ticketService.findTicketsByUser(user);
        if (tickets.isEmpty()) {
            logger.error("getBookedTickets({}) call. No ticket found.", user);
            return tickets;
        }
        logger.debug("getBookedTickets({}) call. Tickets found.", user);
        return tickets.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> tickets = ticketService.findTicketsByEvent(event);
        if (tickets.isEmpty()) {
            logger.error("getBookedTickets({}) call. No ticket found.", event);
            return tickets;
        }
        logger.debug("getBookedTickets({}) call. Tickets found.", event);
        return tickets.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.removeTicketById(ticketId);
    }


}
