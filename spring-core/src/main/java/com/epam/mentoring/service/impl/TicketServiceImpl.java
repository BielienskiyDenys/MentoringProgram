package com.epam.mentoring.service.impl;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.dao.TicketDao;
import com.epam.mentoring.dao.UserDao;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.TicketImpl;
import com.epam.mentoring.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class TicketServiceImpl implements TicketService {
	private static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	private TicketDao ticketDao;
	private UserDao userDao;
	private EventDao eventDao;

	public TicketDao getTicketDao() {
		return ticketDao;
	}
	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}
	public UserDao getUserDao() {return userDao;}
	public void setUserDao(UserDao userDao) {this.userDao = userDao;}
	public EventDao getEventDao() {return eventDao;}
	public void setEventDao(EventDao eventDao) {this.eventDao = eventDao;}

	public Ticket addTicket(long userId, long eventId, int place, Category category) {
		if (userId <= 0 || eventId <= 0 || place <= 0 || place>300 ||category == null) {
			logger.error("addTicket({}, {}, {}, {}) call. Invalid fields.", userId, eventId, place,
					category);
			return new TicketImpl();
		}
		if (userDao.findUserById(userId)!=null && eventDao.findEventById(eventId)!=null && !ticketDao.checkIfTicketSold(eventId, place)) {
		Ticket ticket = new TicketImpl(userId, eventId, place, category);
		logger.debug("addTicket({}, {}, {}, {}) call. Created new ticket with Id {}. Readressing to repository", userId, eventId, place,
				category, ticket.getId());
		ticketDao.addTicket(ticket);
			return ticket;
		}

		logger.error("addTicket({}, {}, {}, {}) call. Failed to create ticket.", userId, eventId, place,
				category);
		return new TicketImpl();
	}
	
	public boolean removeTicketById(Long ticketId) {
		logger.debug("removeTicketById({}) call. Readressing to repository.", ticketId);
		Ticket ticket = ticketDao.removeTicketById(ticketId);
		if (ticket != null) {
			return true;
		}
		logger.error("removeTicketById({}) call. No ticket found. Throwing error.", ticketId);
		return false;
	}
	
	public List<Ticket> findTicketsByUser(User user) {
		logger.debug("findTicketsByUser({}) call. Readressing to repository.", user.getId());
		List<Ticket> filteredTickets = ticketDao.findTicketsByUserId(user.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByUser({}) call. No ticket found.", user.getId());
		return Collections.emptyList();
	}
	
	public List<Ticket> findTicketsByEvent(Event event) {
		logger.debug("findTicketsByEvent({}) call. Readressing to repository.", event.getId());
		List<Ticket> filteredTickets = ticketDao.findTicketsByEventId(event.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByEvent({}) call. No ticket found. Throwing error.", event.getId());
		return Collections.emptyList();
	}
}
