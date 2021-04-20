package com.epam.mentoring.controller;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/events")
public class EventController {
    private static Logger logger = LoggerFactory.getLogger(EventController.class);

    private BookingFacade bookingFacade;

    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    /**
     * Gets event by its id.
     * @param eventId   Event id.
     * @return Event.
     */
    @GetMapping
    public String getEventById(@RequestParam(name="event-id") long eventId, Model model) {
        List<Event> events = new ArrayList<>();
        Event event = bookingFacade.getEventById(eventId);
        if (event != null) {
            events.add(event);
            logger.info("No events found.");
        }
        logger.info("Returning event for id {}.", eventId);
        model.addAttribute("eventsList", events);
        return "events";
    }

    /**
     * Get list of events for specified day. In case nothing was found, empty list
     * is returned.
     *
     * @param dateStr  String from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from
     *                 1.
     * @return List of events.
     */
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
        logger.info("Returning list of events for day {}.", dateStr);
        model.addAttribute("eventsList", events);
        return "events";
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains'
     * approach. In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from
     *                 1.
     * @return List of events.
     */
    @GetMapping("/title")
    public String getEvebtsByTitle(@RequestParam(name="title") String title,
                                 @RequestParam(name="page-size", required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(name="page-num", required = false, defaultValue = "0") int pageNum,
                                 Model model) {
        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize,pageNum);
        logger.info("Returning list of events for title {}.", title);
        model.addAttribute("eventsList", events);
        return "events";
    }

    /**
     * Creates new event.
     *
     * @param eventId             Event id.
     * @param eventTitle          Event title.
     * @param eventDateTimeStr    Event date and time.
     * @return Created Event object.
     */
    @PostMapping
    public String createEvent(@RequestParam(name = "event-id") long eventId,
                              @RequestParam(name = "event-title") String eventTitle,
                              @RequestParam(name = "event-date-time") String eventDateTimeStr,
                              @RequestParam(name = "ticket-price") Double ticketPrice,
                              Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date eventDateTime =format.parse(eventDateTimeStr);
        Event event = new Event();
        event.setId(eventId);
        event.setTitle(eventTitle);
        event.setDate(eventDateTime);
        event.setTicketPrice(ticketPrice);

        List<Event> events = new ArrayList<>();

        logger.info("Attempt to create new event with params id={}, title={}, date={}.", eventId, eventTitle, eventDateTimeStr);
        events.add(bookingFacade.createEvent(event));

        model.addAttribute("eventsList", events);

        return "events";
    }

    /**
     * Updates event using given data.
     *
     * @param eventId             Event id.
     * @param eventTitle          Event title.
     * @param eventDateTimeStr    Event date and time.
     * @return Updated Event object.
     */
    @PostMapping("/update")
    public String updateEvent(@RequestParam(name = "event-id") long eventId,
                              @RequestParam(name = "event-title") String eventTitle,
                              @RequestParam(name = "event-date-time") String eventDateTimeStr,
                              Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date eventDateTime =format.parse(eventDateTimeStr);
        Event event = new Event();
        event.setId(eventId);
        event.setTitle(eventTitle);
        event.setDate(eventDateTime);

        Event newEvent = bookingFacade.updateEvent(event);
        if (newEvent==null) {
            logger.info("Failed to update event with params id={}, title={}, date={}.", eventId, eventTitle, eventDateTimeStr);
            model.addAttribute("eventsMessage", "Event update failure.");
        } else {
            List<Event> events = new ArrayList<>();
            events.add(newEvent);
            logger.info("Updated event with params id={}, title={}, date={}.", eventId, eventTitle, eventDateTimeStr);
            model.addAttribute("eventsList", events);
        }
        return "events";
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam(name = "event-id") long eventId,
                              Model model){
        if(bookingFacade.deleteEvent(eventId)) {
            logger.info("Deleted event with params id={}.", eventId);
            model.addAttribute("eventsMessage", "Successfully deleted event with id: " + eventId);
        } else {
            logger.info("Failed to delete event with params id={}.", eventId);
            model.addAttribute("eventsMessage", "Failed to delete event with id: " + eventId);
        }
        return "events";
    }


}
