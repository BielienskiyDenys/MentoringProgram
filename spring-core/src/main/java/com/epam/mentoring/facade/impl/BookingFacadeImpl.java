package com.epam.mentoring.facade.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.TicketImpl;
import com.epam.mentoring.service.EventService;
import com.epam.mentoring.service.TicketService;
import com.epam.mentoring.service.UserService;

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
		Event event = null;
		try {
			event = eventService.findEventById(eventId);
			logger.debug("getEventById({}) call. Event found.", eventId);
		} catch (EntryNotFoundException e) {
			logger.error("getEventById({}) call. No event found.", eventId);

		}
		return event;
	}

	@Override
	public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
		List<Event> events = null;
		try {
			events = eventService.findEventsByTitle(title);
			logger.debug("getEventsByTitle({}) call. User found.", title);
		} catch (EntryNotFoundException e) {
			logger.error("getEventsByTitle({}) call. No user found.", title);
		}
		return events.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
	}

	@Override
	public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
		List<Event> events = null;
		try {
			events = eventService.findEventsByDate(day);
			logger.debug("getEventsForDay({}) call. User found.", day);
		} catch (EntryNotFoundException e) {
			logger.error("getEventsForDay({}) call. No user found.", day);
		}
		return events.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
	}

	@Override
	public Event createEvent(Event event) {
		try {
			eventService.addEvent(event);
			logger.debug("createEvent({}) call. Event created.", event);
		} catch (EntryExistsAlreadyException e) {
			logger.error("createEvent({}) call. Event already exists.", event);
		} catch (EntryValidationException e) {
			logger.error("createEvent({}) call. Mandatory fields missing in event.", event);
		}
		return event;
	}

	@Override
	public Event updateEvent(Event event) {
		try {
			eventService.updateEvent(event);
			logger.debug("updateEvent({}) call. Event updated.", event);
		} catch (EntryNotFoundException e) {
			logger.error("updateEvent({}) call. No event found to update.", event);
		} catch (EntryValidationException e) {
			logger.error("updateEvent({}) call. Mandatory fields missing in event.", event);
		}
		return event;
	}

	@Override
	public boolean deleteEvent(long eventId) {
		try {
			eventService.removeEventById(eventId);
			logger.debug("deleteEvent({}) call. Event deleted.", eventId);
		} catch (EntryNotFoundException e) {
			logger.debug("deleteEvent({}) call. No event found to delete.", eventId);
			return false;
		}
		return true;
	}

	@Override
	public User getUserById(long userId) {
		User user = null;
		try {
			user = userService.findUserById(userId);
			logger.debug("getUserById({}) call. User found.", userId);
		} catch (EntryNotFoundException e) {
			logger.error("getUserById({}) call. No user found.", userId);

		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = userService.findUserByEmail(email);
			logger.debug("getUserByEmail({}) call. User found.", email);
		} catch (EntryNotFoundException e) {
			logger.error("getUserByEmail({}) call. No user found.", email);
		}
		return user;
	}

	@Override
	public List<User> getUsersByName(String name, int pageSize, int pageNum) {
		List<User> users = null;
		try {
			users = userService.findUsersByName(name);
			logger.debug("getUserByName({}) call. User found.", name);
		} catch (EntryNotFoundException e) {
			logger.error("getUserByName({}) call. No user found.", name);
		}
		return users.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
	}

	@Override
	public User createUser(User user) {
		try {
			userService.addUser(user);
			logger.debug("createUser({}) call. User created.", user);
		} catch (EntryExistsAlreadyException e) {
			logger.error("createUser({}) call. User already exists.", user);
		} catch (EntryValidationException e) {
			logger.error("createUser({}) call. Mandatory fields missing in user.", user);
		}
		return user;
	}

	@Override
	public User updateUser(User user) {
		try {
			userService.updateUser(user);
			logger.debug("updateUser({}) call. User updated.", user);
		} catch (EntryNotFoundException e) {
			logger.error("updateUser({}) call. No user found to update.", user);
		} catch (EntryValidationException e) {
			logger.error("updateUser({}) call. Mandatory fields missing in user.", user);
		}
		return user;
	}

	@Override
	public boolean deleteUser(long userId) {
		try {
			userService.removeUserById(userId);
			logger.debug("deleteUser({}) call. User deleted.", userId);
		} catch (EntryNotFoundException e) {
			logger.debug("deleteUser({}) call. No user found to delete.", userId);
			return false;
		}
		return true;
	}

	@Override
	public Ticket bookTicket(long userId, long eventId, int place, Category category) {
		try {
			Ticket ticket = ticketService.addTicket(userId, eventId, place, category);
			logger.debug("bookTicket({}, {}, {}, {}) call. Ticket created.", userId, eventId, place, category);
			return ticket;
		} catch (EntryValidationException e) {
			logger.error("bookTicket({}, {}, {}, {}) call. Mandatory fields missing in user.", userId, eventId, place, category);
			return new TicketImpl();
		}
	
	}

	@Override
	public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
		List<Ticket> tickets = null;
		try {
			tickets = ticketService.findTicketsByUser(user);
			logger.debug("getBookedTickets({}) call. Tickets found.", user);
		} catch (EntryNotFoundException e) {
			logger.error("getBookedTickets({}) call. No ticket found.", user);
		}
		return tickets.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
	}

	@Override
	public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
		List<Ticket> tickets = null;
		try {
			tickets = ticketService.findTicketsByEvent(event);
			logger.debug("getBookedTickets({}) call. Tickets found.", event);
		} catch (EntryNotFoundException e) {
			logger.error("getBookedTickets({}) call. No ticket found.", event);
		}
		return tickets.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
	}

	@Override
	public boolean cancelTicket(long ticketId) {
		try {
			ticketService.removeTicketById(ticketId);
			logger.debug("cancelTicket({}) call. Ticket deleted.", ticketId);
		} catch (EntryNotFoundException e) {
			logger.debug("cancelTicket({}) call. No ticket found to delete.", ticketId);
			return false;
		}
		return true;
	}
}
