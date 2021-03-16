package com.epam.mentoring.dao;

import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.Ticket;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.EventImpl;
import com.epam.mentoring.model.impl.TicketImpl;
import com.epam.mentoring.model.impl.UserImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Storage {
    private Map<Long, User> users;
    private Map<Long, Event> events;
    private Map<Long, Ticket> tickets;

    private void init() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("springCoreTaskStorage.properties");
        ObjectMapper mapper = new ObjectMapper();

        users = new HashMap<>();
        List<UserImpl> userList = mapper.readValue(properties.getProperty("users"), new TypeReference<List<UserImpl>>() {});
        userList.forEach(e -> users.put(e.getId(), e));

        events = new HashMap<>();
        List<EventImpl> eventList = mapper.readValue(properties.getProperty("events"), new TypeReference<List<EventImpl>>() {});
        eventList.forEach(e -> events.put(e.getId(), e));

        tickets = new HashMap<>();
        List<TicketImpl> ticketList = mapper.readValue(properties.getProperty("tickets"), new TypeReference<List<TicketImpl>>() {});
        ticketList.forEach(e -> tickets.put(e.getId(), e));

    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, User> users) {
        this.users = users;
    }

    public Map<Long, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<Long, Event> events) {
        this.events = events;
    }

    public Map<Long, Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Map<Long, Ticket> tickets) {
        this.tickets = tickets;
    }
}
