package com.epam.mentoring.dao;

import com.epam.mentoring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);
	private Storage storage;

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public void addUser(User user) {
		logger.debug("addUser({}) call.", user);
		storage.getUsers().put(user.getId(), user);
	}

	public User removeUserById(Long userId) {
		logger.debug("removeUserById({}) call.", userId);
		return storage.getUsers().remove(userId);
	}

	public User findUserById(long userId) {
		logger.debug("findUserById({}) call.", userId);
		return storage.getUsers().get(userId);
	}

	public List<User> findUsersByEmail(String userEmail) {
		logger.debug("findUsersByEmail({}) call.", userEmail);
		return storage.getUsers().values().stream()
				.filter(user -> user.getEmail().equals(userEmail))
				.collect(Collectors.toList());
	}

	public List<User> findUsersByName(String userName) {
		logger.debug("findUsersByName({}) call.", userName);
		return storage.getUsers().values().stream()
				.filter(user -> user.getName().equals(userName))
				.collect(Collectors.toList());
	}

	public User updateUser(User user) {
		logger.debug("updateUser({}) call.", user);
		return storage.getUsers().put(user.getId(), user);
	}
}
