package com.epam.mentoring.service;

import java.util.List;

import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.Ticket.Category;

public interface TicketService {
	public Ticket addTicket(long userId, long eventId, int place, Category category) throws EntryValidationException;
	
	public Ticket removeTicketById(Long ticketId) throws EntryNotFoundException;
	
	public List<Ticket> findTicketsByUser(User user) throws EntryNotFoundException;
	
	public List<Ticket> findTicketsByEvent(Event event) throws EntryNotFoundException;
}
