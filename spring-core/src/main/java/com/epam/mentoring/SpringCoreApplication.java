package com.epam.mentoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Ticket.Category;
import com.epam.mentoring.service.EventService;
import com.epam.mentoring.service.UserService;

@SpringBootApplication
public class SpringCoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SpringApplication.run(SpringCoreApplication.class, args);


		UserService userService = context.getBean("userService", UserService.class);
		String name = userService.findUserById(355L).getName();
		System.out.println("userService check " + name);

		EventService eventService = context.getBean("eventService", EventService.class);
		String title = eventService.findEventById(12L).getTitle();

		System.out.println("eventService check " + title);

		BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);
		System.out.println("bookingFacade check users " + bookingFacade.getUserById(366L).getName() +
				"; check events " + bookingFacade.getEventById(10L).getTitle() +
				"; check tickets " + bookingFacade.bookTicket(333, 12, 54, Category.PREMIUM));
	}

}
