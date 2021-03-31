package com.epam.module06boottask2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title should be present")
    @Size(min = 3, max = 50, message = "Title should be between 3 and 50 characters")
    private String title;

    @NotNull(message = "Place should be present")
    @Column(name = "place_id")
    private Place place;

    @Size(min = 3, max = 50, message = "Title should be between 3 and 50 characters")
    private String speaker;

    @NotNull(message = "Event type should be present")
    @Column(name = "event_type_id")
    private EventType eventType;

    @NotNull(message = "Date should be present")
    @FutureOrPresent(message = "Date should be in future or today")
    @Column(name = "date")
    private Date dateTime;

}
