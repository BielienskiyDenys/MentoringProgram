package com.epam.mentoring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class User {
	@Id
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

	public User(@Positive(message = "User id should not be less than 1") long id, @Size(min = 3, max = 50, message
			= "Name should be between 3 and 50 characters") String name, @Email(message = "Email should be valid") String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

}
