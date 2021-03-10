package com.epam.mentoring.model.impl;

import com.epam.mentoring.model.Ticket;

public class TicketImpl implements Ticket {
	private long id;
	private long eventId;
	private long userId;
	private Category category;
	private int place;

	public TicketImpl(long userId, long eventId, int place, Category category) {
		this.id = System.currentTimeMillis();
		this.userId = userId;
		this.eventId = eventId;
		this.place = place;
		this.category = category;
	}

	public TicketImpl() {
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getEventId() {
		return eventId;
	}

	@Override
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	@Override
	public long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public Category getCategory() {
		return category;
	}

	@Override
	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int getPlace() {
		return place;
	}

	@Override
	public void setPlace(int place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "id=" + id + ", eventId=" + eventId + ", userId=" + userId + ", category=" + category + ", place="
				+ place;
	}

}
