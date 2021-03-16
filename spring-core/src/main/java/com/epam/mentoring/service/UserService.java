package com.epam.mentoring.service;

import com.epam.mentoring.model.User;

import java.util.List;

public interface UserService {
	public boolean addUser(User user);

	public boolean removeUserById(Long userId);

	public User findUserById(long userId);

	public User findUserByEmail(String userEmail);

	public List<User> findUsersByName(String userName);

	public boolean updateUser(User user);
}
