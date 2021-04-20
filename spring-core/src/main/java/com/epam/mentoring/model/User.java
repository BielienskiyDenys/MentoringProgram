package com.epam.mentoring.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public User() {

	}

	public User(@Positive(message = "User id should not be less than 1") long id, @Size(min = 3, max = 50, message
			= "Name should be between 3 and 50 characters") String name, @Email(message = "Email should be valid") String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", email=" + email;
	}

}
