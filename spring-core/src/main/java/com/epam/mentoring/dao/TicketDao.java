package com.epam.mentoring.dao;

import com.epam.mentoring.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketDao {
	private static Logger logger = LoggerFactory.getLogger(TicketDao.class);
	private Storage storage;

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public void addTicket(Ticket ticket) {
		logger.debug("addTicket({}) call.", ticket);
		storage.getTickets().put(ticket.getId(), ticket);
	}
	
	public Ticket removeTicketById(Long ticketId) {
		logger.debug("removeTicketById({}) call.", ticketId);
		return storage.getTickets().remove(ticketId);
	}
	
	public List<Ticket> findTicketsByUserId (long userId) {
		logger.debug("findTicketsByUserId({}) call.", userId);
		return storage.getTickets().values().stream()
				.filter(ticket -> ticket.getUserId()==userId)
				.collect(Collectors.toList());
	}
	
	public List<Ticket> findTicketsByEventId (long eventId) {
		logger.debug("findTicketsByEventId({}) call.", eventId);
		return storage.getTickets().values().stream()
				.filter(ticket -> ticket.getEventId()==eventId)
				.collect(Collectors.toList());
	}

	public boolean checkIfTicketSold (long eventId, int place) {
		logger.debug("checkIfTicketSold({}, {}) call.", eventId, place);
		return storage.getTickets().values().stream()
				.anyMatch(ticket -> ticket.getEventId() == eventId && ticket.getPlace() == place);
	}

}
