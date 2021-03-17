package com.epam.mentoring.model.impl;

import com.epam.mentoring.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UserImpl implements User {
	@NotNull
	@Positive(message = "User id should not be less than 1")
	private long id;

	@NotNull
	@Size(min = 3, max = 50, message
			= "Name should be between 3 and 50 characters")
	private String name;

	@NotNull
	@Email(message = "Email should be valid")
	private String email;


	public UserImpl() {

	}

	public UserImpl(@Positive(message = "User id should not be less than 1") long id, @Size(min = 3, max = 50, message
			= "Name should be between 3 and 50 characters") String name, @Email(message = "Email should be valid") String email) {
		this.id = id;
		this.name = name;
		this.email = email;
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
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", email=" + email;
	}

}
