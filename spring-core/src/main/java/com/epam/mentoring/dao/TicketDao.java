package com.epam.mentoring.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class TicketDao {
	private static Logger logger = LoggerFactory.getLogger(TicketDao.class);
	private Map<Long, Ticket> tickets;

	public Map<Long, Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Map<Long, Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public void addTicket(Ticket ticket) {
		logger.debug("addTicket({}) call.", ticket);
		tickets.put(ticket.getId(), ticket);
	}
	
	public Ticket removeTicketById(Long ticketId) {
		logger.debug("removeTicketById({}) call.", ticketId);
		return tickets.remove(ticketId);
	}
	
	public List<Ticket> findTicketsByUserId (long userId) {
		logger.debug("findTicketsByUserId({}) call.", userId);
		return tickets.values().stream()
				.filter(ticket -> ticket.getUserId()==userId)
				.collect(Collectors.toList());
	}
	
	public List<Ticket> findTicketsByEventId (long eventId) {
		logger.debug("findTicketsByEventId({}) call.", eventId);
		return tickets.values().stream()
				.filter(ticket -> ticket.getEventId()==eventId)
				.collect(Collectors.toList());
	}

	public boolean checkIfTicketSold (long eventId, int place) {
		logger.debug("checkIfTicketSold({}, {}) call.", eventId, place);
		return tickets.values().stream()
				.anyMatch(ticket -> ticket.getEventId() == eventId && ticket.getPlace() == place);
	}

}
