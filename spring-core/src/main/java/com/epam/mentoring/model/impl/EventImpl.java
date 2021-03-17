package com.epam.mentoring.model.impl;

import java.util.Date;

import com.epam.mentoring.model.Event;

import javax.validation.constraints.*;

public class EventImpl implements Event {
	@NotNull
	@Positive(message = "Event id should not be less than 1")
	private long id;

	@NotNull
	@Size(min = 3, max = 50, message
			= "Title must be between 3 and 50 characters")
	private String title;

	@NotNull
	@FutureOrPresent(message = "Events can only be created for today or the future")
	private Date date;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "id=" + id + ", title=" + title + ", date=" + date;
	}

}
