package com.epam.rest.batch;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.epam.rest.entity.Event;
import com.epam.rest.entity.EventType;
import com.epam.rest.entity.Place;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import sun.security.krb5.internal.Ticket;

import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventConverter implements Converter{
    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Event.class);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        // Don't do anything
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        Event event = new Event();
        event.setId(Long.valueOf(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        event.setTitle(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        event.setPlace(Place.valueOf(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        event.setSpeaker(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        event.setEventType(EventType.valueOf(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        event.setDateTime(Date.from(Instant.parse(reader.getValue())));

        return event;
    }
}
