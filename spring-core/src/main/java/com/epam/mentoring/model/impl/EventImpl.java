package com.epam.mentoring.model.impl;

import java.util.Date;

import com.epam.mentoring.model.Event;

public class EventImpl implements Event {
	private long id;
	private String title;
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
