package com.epam.mentoring.dao;

import com.epam.mentoring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);
	private Map<Long, User> users;

	public Map<Long, User> getUsers() {
		return users;
	}

	public void setUsers(Map<Long, User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		logger.debug("addUser({}) call.", user);
		users.put(user.getId(), user);
	}

	public User removeUserById(Long userId) {
		logger.debug("removeUserById({}) call.", userId);
		return users.remove(userId);
	}

	public User findUserById(long userId) {
		logger.debug("findUserById({}) call.", userId);
		return users.get(userId);
	}

	public List<User> findUsersByEmail(String userEmail) {
		logger.debug("findUsersByEmail({}) call.", userEmail);
		return users.values().stream()
				.filter(user -> user.getEmail().equals(userEmail))
				.collect(Collectors.toList());
	}

	public List<User> findUsersByName(String userName) {
		logger.debug("findUsersByName({}) call.", userName);
		return users.values().stream()
				.filter(user -> user.getName().equals(userName))
				.collect(Collectors.toList());
	}

	public User updateUser(User user) {
		logger.debug("updateUser({}) call.", user);
		return users.put(user.getId(), user);
	}
}
