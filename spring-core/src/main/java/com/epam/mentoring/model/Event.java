package com.epam.mentoring.model;

import lombok.Data;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "events")
public class Event{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Positive(message = "Event id should not be less than 1")
	private long id;

	@NotNull
	@Size(min = 3, max = 50, message
			= "Title must be between 3 and 50 characters")
	private String title;

	@NotNull
	@Temporal(TemporalType.DATE)
	@FutureOrPresent(message = "Events can only be created for today or the future")
	private Date date;

	@NotNull
	@PositiveOrZero(message = "Event ticket price should not be less than 0")
	private double ticketPrice;
}
