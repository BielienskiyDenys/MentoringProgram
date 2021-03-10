package com.epam.mentoring.service;

import java.util.List;

import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.User;

public interface UserService {
	public void addUser(User user) throws EntryExistsAlreadyException, EntryValidationException;

	public User removeUserById(Long userId) throws EntryNotFoundException;

	public User findUserById(long userId) throws EntryNotFoundException;

	public User findUserByEmail(String userEmail) throws EntryNotFoundException;

	public List<User> findUsersByName(String userName) throws EntryNotFoundException;

	public User updateUser(User user) throws EntryNotFoundException, EntryValidationException;
}
