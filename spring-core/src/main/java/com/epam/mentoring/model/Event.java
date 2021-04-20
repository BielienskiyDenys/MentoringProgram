package com.epam.mentoring.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Override
	public String toString() {
		return "id=" + id + ", title=" + title + ", date=" + date;
	}

}
