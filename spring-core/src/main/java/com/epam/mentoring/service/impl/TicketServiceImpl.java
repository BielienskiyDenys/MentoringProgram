package com.epam.mentoring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.dao.TicketDao;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.impl.TicketImpl;
import com.epam.mentoring.service.TicketService;
import org.springframework.stereotype.Service;

public class TicketServiceImpl implements TicketService {
	private static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	private TicketDao ticketDao;

	public TicketDao getTicketDao() {
		return ticketDao;
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public Ticket addTicket(long userId, long eventId, int place, Category category) throws EntryValidationException {
		// possible check for the combination of eventId and place to exist
		// possible check if user with such id exists
		// possible check if event with such id exists
		if (userId <= 0 || eventId <= 0 || place <= 0 || category == null) {
			logger.error("addTicket({}, {}, {}, {}) call. Invalid fields. Throwing error.", userId, eventId, place,
					category);
			throw new EntryValidationException();
		}
		Ticket ticket = new TicketImpl(userId, eventId, place, category);
		logger.debug("addTicket({}, {}, {}, {}) call. Created new ticket with Id {}. Readressing to repository", userId, eventId, place,
				category, ticket.getId());
		ticketDao.addTicket(ticket);
		return ticket;
	}
	
	public Ticket removeTicketById(Long ticketId) throws EntryNotFoundException {
		logger.debug("removeTicketById({}) call. Readressing to repository.", ticketId);
		Ticket ticket = ticketDao.removeTicketById(ticketId);
		if (ticket != null) {
			return ticket;
		}
		logger.error("removeTicketById({}) call. No ticket found. Throwing error.", ticketId);
		throw new EntryNotFoundException("Ticket not found.");
	}
	
	public List<Ticket> findTicketsByUser(User user) throws EntryNotFoundException {
		logger.debug("findTicketsByUser({}) call. Readressing to repository.", user.getId());
		List<Ticket> filteredTickets = ticketDao.findTicketsByUserId(user.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByUser({}) call. No ticket found. Throwing error.", user.getId());
		throw new EntryNotFoundException("Ticket not found.");
	}
	
	public List<Ticket> findTicketsByEvent(Event event) throws EntryNotFoundException {
		logger.debug("findTicketsByEvent({}) call. Readressing to repository.", event.getId());
		List<Ticket> filteredTickets = ticketDao.findTicketsByEventId(event.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByEvent({}) call. No ticket found. Throwing error.", event.getId());
		throw new EntryNotFoundException("Ticket not found.");
	}
}
