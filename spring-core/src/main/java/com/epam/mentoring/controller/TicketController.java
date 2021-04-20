package com.epam.mentoring.controller;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Category;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/tickets")
public class TicketController {
    private BookingFacade bookingFacade;

    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping
    public String bookTicket(@RequestParam(name = "user-id") long userId,
                             @RequestParam(name = "event-id") long eventId,
                             @RequestParam(name = "place") int place,
                             @RequestParam(name = "category-id", defaultValue = "0") int categoryId,
                             Model model) {

        Ticket ticket = bookingFacade.bookTicket(userId, eventId, place, Category.values()[categoryId]);
        if (ticket == null) {
            model.addAttribute("ticketsMessage", "Failed to book ticket");
        } else {
            model.addAttribute("ticketsMessage", "Successfully booked ticket");
            List<Ticket> tickets = new ArrayList<>();
            tickets.add(ticket);
            model.addAttribute("ticketsList", tickets);
        }
        return "tickets";
    }

    @GetMapping("/find")
    public String getTickets(@RequestParam(name = "criteria", defaultValue = "user") String criteria,
                             @RequestParam(name = "entity-id") long entityId,
                             @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(name = "page-num", required = false, defaultValue = "0") int pageNum,
                             Model model) {
        List<Ticket> tickets;
        if ("user".equals(criteria)) {
            User user = bookingFacade.getUserById(entityId);
            tickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        } else {
            Event event = bookingFacade.getEventById(entityId);
            tickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
        }

        model.addAttribute("ticketsList", tickets);
        return "tickets";
    }

    @PostMapping("/delete")
    public String CancelTicket(@RequestParam(name = "ticket-id") long ticketId,
                               Model model) {
        if (bookingFacade.cancelTicket(ticketId)) {
            model.addAttribute("ticketsMessage", "Successfully canceled ticket with id: " + ticketId);
        } else {
            model.addAttribute("ticketsMessage", "Failed to cancel ticket with id: " + ticketId);
        }
        return "tickets";
    }

}
