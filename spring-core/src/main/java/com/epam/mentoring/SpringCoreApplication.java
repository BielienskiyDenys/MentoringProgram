package com.epam.mentoring;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.mentoring.dao.EventDao;
import com.epam.mentoring.dao.TicketDao;
import com.epam.mentoring.dao.UserDao;
import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.model.impl.TicketImpl;
import com.epam.mentoring.model.impl.UserImpl;
import com.epam.mentoring.service.EventService;
import com.epam.mentoring.service.TicketService;
import com.epam.mentoring.service.UserService;

@SpringBootApplication
public class SpringCoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SpringApplication.run(SpringCoreApplication.class, args);

		User testUser = context.getBean("user333", UserImpl.class);
		System.out.println("User id is " + testUser.getId() + "; name is " + testUser.getName() + "; email is "
				+ testUser.getEmail());

		Event testEvent = context.getBean("event10", EventImpl.class);
		System.out.println("Event id is " + testEvent.getId() + "; title is " + testEvent.getTitle() + "; date is "
				+ testEvent.getDate());

		Ticket testTicket = context.getBean("ticket700", TicketImpl.class);
		System.out.println("Ticket id is " + testTicket.getId() + "; eventId is " + testTicket.getEventId()
				+ "; userId is " + testTicket.getUserId() + "; category is " + testTicket.getCategory().toString()
				+ "; place is " + testTicket.getPlace());

		UserDao userDao = context.getBean("userDao", UserDao.class);
		System.out.println("userDao keys " + userDao.getUsers().keySet() + "; values " + userDao.getUsers().values());

		EventDao eventDao = context.getBean("eventDao", EventDao.class);
		System.out.println(
				"eventDao keys " + eventDao.getEvents().keySet() + "; values " + eventDao.getEvents().values());

		TicketDao ticketDao = context.getBean("ticketDao", TicketDao.class);
		System.out.println(
				"ticketDao keys " + ticketDao.getTickets().keySet() + "; values " + ticketDao.getTickets().values());

		UserService userService = context.getBean("userService", UserService.class);
		String name = userService.findUserById(355L).getName();
		System.out.println("userService check " + name);
		
		EventService eventService = context.getBean("eventService", EventService.class);
		String title = eventService.findEventById(12L).getTitle();

		System.out.println("eventService check " + title);
		
		TicketService ticketService = context.getBean("ticketService", TicketService.class);
		List<Ticket> ticketList = ticketService.findTicketsByEvent(testEvent);
		System.out.println("ticketService check " + ticketList);

		BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);
		System.out.println("bookingFacade check users " + bookingFacade.getUserById(366L).getName() +
				"; check events " + bookingFacade.getEventById(10L).getTitle() +
				"; check tickets " + bookingFacade.bookTicket(333, 12, 54, Category.PREMIUM));
	}

}
