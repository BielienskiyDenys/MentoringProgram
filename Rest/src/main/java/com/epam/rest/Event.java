package com.epam.rest;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "place_id")
    private Place place;

    @Column(name = "speaker")
    private String speaker;

    @Column(name = "event_type_id")
    private EventType eventType;

    @Column(name = "date")
    private Date dateTime;

}
