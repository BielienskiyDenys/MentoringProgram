package com.epam.mentoring.controller;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.impl.EventImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/events")
public class EventController {
    private BookingFacade bookingFacade = (new ClassPathXmlApplicationContext("beans.xml")).getBean("bookingFacade", BookingFacade.class);

    @GetMapping
    public String getAllEvents(@RequestParam(name="event-id") long eventId, Model model) {
        List<Event> events = new ArrayList<>();
        Event event = bookingFacade.getEventById(eventId);
        if (event != null) {
            events.add(event);
        }
        model.addAttribute("eventsList", events);
        return "events";
    }

    @GetMapping("/day")
    public String getEventsByDay(@RequestParam(name= "day") String dateStr,
                                 @RequestParam(name="page-size", required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(name="page-num", required = false, defaultValue = "0") int pageNum,
                                 Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if(dateStr.equals("")) {
            date = Date.from(Instant.now());
        } else {
            date = format.parse(dateStr);
        }
        List<Event> events = bookingFacade.getEventsForDay(date, pageSize,pageNum);
        model.addAttribute("eventsList", events);
        return "events";
    }

    @GetMapping("/title")
    public String getEvebtsByTitle(@RequestParam(name="title") String title,
                                 @RequestParam(name="page-size", required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(name="page-num", required = false, defaultValue = "0") int pageNum,
                                 Model model) {
        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize,pageNum);
        model.addAttribute("eventsList", events);
        return "events";
    }

    @PostMapping
    public String createEvent(@RequestParam(name = "event-id") long eventId,
                              @RequestParam(name = "event-title") String eventTitle,
                              @RequestParam(name = "event-date-time") String eventDateTimeStr,
                              Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date eventDateTime =format.parse(eventDateTimeStr);
        Event event = new EventImpl();
        event.setId(eventId);
        event.setTitle(eventTitle);
        event.setDate(eventDateTime);

        List<Event> events = new ArrayList<>();
        events.add(bookingFacade.createEvent(event));

        model.addAttribute("eventsList", events);

        return "events";
    }


}
