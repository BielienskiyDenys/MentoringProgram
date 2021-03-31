package com.epam.module06boottask2.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Could not find any event");
    }
    public EventNotFoundException(long id) {
        super("Could not find event " + id);
    }
}
