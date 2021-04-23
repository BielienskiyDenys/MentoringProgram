package com.epam.mentoring.service.impl;

import com.epam.mentoring.model.Category;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.repo.EventRepo;
import com.epam.mentoring.repo.TicketRepo;
import com.epam.mentoring.repo.UserRepo;
import com.epam.mentoring.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
	private static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	private TicketRepo ticketRepo;
	private UserRepo userRepo;
	private EventRepo eventRepo;

	public TicketServiceImpl(TicketRepo ticketRepo, UserRepo userRepo, EventRepo eventRepo) {
		this.ticketRepo = ticketRepo;
		this.userRepo = userRepo;
		this.eventRepo = eventRepo;
	}

	@Override
	@Transactional
	public Ticket addTicket(long userId, long eventId, int place, Category category) {
		if (userId <= 0 || eventId <= 0 || place <= 0 || place>300 ||category == null) {
			logger.error("addTicket({}, {}, {}, {}) call. Invalid fields.", userId, eventId, place,
					category);
			return null;
		}
		if (userRepo.findById(userId).isPresent() && eventRepo.findById(eventId).isPresent() && ticketRepo.findByEventIdAndPlace(eventId, place).isEmpty()) {
		Ticket ticket = new Ticket(userId, eventId, place, category);
		logger.debug("addTicket({}, {}, {}, {}) call. Created new ticket with Id {}. Readressing to repository", userId, eventId, place,
				category, ticket.getId());
		ticket = ticketRepo.save(ticket);
			return ticket;
		}

		logger.error("addTicket({}, {}, {}, {}) call. Failed to create ticket.", userId, eventId, place,
				category);
		return null;
	}

	@Override
	@Transactional
	public boolean removeTicketById(Long ticketId) {
		logger.debug("removeTicketById({}) call. Readressing to repository.", ticketId);
		Optional<Ticket> ticket = ticketRepo.findById(ticketId);
		if (ticket.isPresent()) {
			ticketRepo.deleteById(ticketId);
			return true;
		}
		logger.error("removeTicketById({}) call. No ticket found. Throwing error.", ticketId);
		return false;
	}

	@Override
	public List<Ticket> findTicketsByUser(User user) {
		logger.debug("findTicketsByUser({}) call. Readressing to repository.", user.getId());
		List<Ticket> filteredTickets = ticketRepo.findByUserId(user.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByUser({}) call. No ticket found.", user.getId());
		return Collections.emptyList();
	}

	@Override
	public List<Ticket> findTicketsByEvent(Event event) {
		logger.debug("findTicketsByEvent({}) call. Readressing to repository.", event.getId());
		List<Ticket> filteredTickets = ticketRepo.findByEventId(event.getId());
		if (!filteredTickets.isEmpty()) {
			return filteredTickets;
		}
		logger.error("findTicketsByEvent({}) call. No ticket found. Throwing error.", event.getId());
		return Collections.emptyList();
	}
}
