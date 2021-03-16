package com.epam.mentoring.service;

import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.User;

import java.util.List;

public interface TicketService {
	public Ticket addTicket(long userId, long eventId, int place, Category category);
	
	public boolean removeTicketById(Long ticketId);
	
	public List<Ticket> findTicketsByUser(User user);
	
	public List<Ticket> findTicketsByEvent(Event event);
}
