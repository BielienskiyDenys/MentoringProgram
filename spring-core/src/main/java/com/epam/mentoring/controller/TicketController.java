package com.epam.mentoring.controller;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.UserImpl;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.service.TicketPdfExporter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/tickets")
public class TicketController {
    private BookingFacade bookingFacade = (new ClassPathXmlApplicationContext("beans.xml")).getBean("bookingFacade", BookingFacade.class);
//
//    @GetMapping("/id")
//    public String getUserById(@RequestParam(name="user-id") long userId, Model model) {
//        List<User> users = new ArrayList<>();
//        User user = bookingFacade.getUserById(userId);
//        if (user != null) {
//            users.add(user);
//        }
//        model.addAttribute("usersList", users);
//        return "users";
//    }
//
//    @GetMapping("/email")
//    public String getUserByEmail(@RequestParam(name="email") String email, Model model) {
//        List<User> users = new ArrayList<>();
//        User user = bookingFacade.getUserByEmail(email);
//        if (user != null) {
//            users.add(user);
//        }
//        model.addAttribute("usersList", users);
//        return "users";
//    }
//
//    @GetMapping("/name")
//    public String getUsersByName(@RequestParam(name="name") String name,
//                                 @RequestParam(name="page-size", required = false, defaultValue = "10") int pageSize,
//                                 @RequestParam(name="page-num", required = false, defaultValue = "0") int pageNum,
//                                 Model model) {
//        List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
//
//        model.addAttribute("usersList", users);
//        return "users";
//    }


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

    @GetMapping(value = "/getpdf")
    public void getTicketsPdf( HttpServletResponse response,
                                  @RequestParam(name = "criteria", defaultValue = "user") String criteria,
                                  @RequestParam(name = "entity-id") long entityId,
                                  Model model) throws IOException {
        List<Ticket> tickets;
        if ("user".equals(criteria)) {
            User user = bookingFacade.getUserById(entityId);
            tickets = bookingFacade.getBookedTickets(user, Integer.MAX_VALUE, 0);
        } else {
            Event event = bookingFacade.getEventById(entityId);
            tickets = bookingFacade.getBookedTickets(event, Integer.MAX_VALUE, 0);
        }

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tickets_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        TicketPdfExporter exporter = new TicketPdfExporter(tickets);
        exporter.export(response);
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

    @PostMapping("/batch")
    public String uploadBatch(Model model) {

        return "tickets";
    }


}
