package com.epam.rest;

public class EventNotFoundException extends RuntimeException {
    EventNotFoundException() {
        super("Could not find any event");
    }
    EventNotFoundException(long id) {
        super("Could not find event " + id);
    }
}
