package com.epam.mentoring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mentoring.dao.UserDao;
import com.epam.mentoring.exceptions.EntryExistsAlreadyException;
import com.epam.mentoring.exceptions.EntryNotFoundException;
import com.epam.mentoring.exceptions.EntryValidationException;
import com.epam.mentoring.model.User;
import com.epam.mentoring.service.UserService;

public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void addUser(User user) throws EntryExistsAlreadyException, EntryValidationException {
		validateUserFields(user);

		if (userDao.findUserById(user.getId()) == null && userDao.findUsersByEmail(user.getEmail()).isEmpty()) {
			logger.debug("addUser({}) call. Readressing to repository.", user);
			userDao.addUser(user);
		}
		logger.error("addUser({}) call. No user found. Throwing error.", user);
		throw new EntryExistsAlreadyException("User already registered.");
	}

	public User removeUserById(Long userId) throws EntryNotFoundException {
		User user = userDao.findUserById(userId);
		if (user != null) {
			logger.debug("removeUserById({}) call. Readressing to repository.", userId);
			return userDao.removeUserById(userId);
		}
		logger.error("removeUserById({}) call. No user found. Throwing error.", userId);
		throw new EntryNotFoundException("User not found.");
	}

	public User findUserById(long userId) throws EntryNotFoundException {
		User user = userDao.findUserById(userId);
		if (user != null) {
			logger.debug("findUserById({}) call. Readressing to repository.", userId);
			return user;
		}
		logger.error("findUserById({}) call. No user found. Throwing error.", userId);
		throw new EntryNotFoundException("User not found.");
	}

	public User findUserByEmail(String userEmail) throws EntryNotFoundException {
		List<User> resultList = userDao.findUsersByEmail(userEmail);
		// possible check for more than one element in list -> emails are not unique
		if (!resultList.isEmpty()) {
			logger.debug("findUserByEmail({}) call. Readressing to repository.", userEmail);
			return resultList.get(0);
		}
		logger.error("findUserByEmail({}) call. No user found. Throwing error.", userEmail);
		throw new EntryNotFoundException("User not found.");
	}

	public List<User> findUsersByName(String userName) throws EntryNotFoundException {
		logger.debug("findUsersByName({}) call. Readressing to repository.", userName);
		List<User> filteredUsers = userDao.findUsersByName(userName);
		if (!filteredUsers.isEmpty()) {
			return filteredUsers;
		}
		logger.error("findUsersByName({}) call. No user found. Throwing error.", userName);
		throw new EntryNotFoundException("User not found.");
	}

	public User updateUser(User user) throws EntryNotFoundException, EntryValidationException {
		validateUserFields(user);
		if (userDao.findUserById(user.getId()) != null) {
			logger.debug("updateUser({}) call. Readressing to repository.", user);
			return userDao.updateUser(user);
		}
		logger.error("updateUser({}) call. No user found. Throwing error.", user);
		throw new EntryNotFoundException("User not found.");
	}

	private void validateUserFields(User user) throws EntryValidationException {
		if (user.getEmail() == null) {
			logger.error("validateUserFields({}) call. No email found. Throwing error.", user);
			throw new EntryValidationException("Email is mandatory.");
		}
		if (user.getName() == null) {
			logger.error("validateUserFields({}) call. No name found. Throwing error.", user);
			throw new EntryValidationException("Name is mandatory.");
		}
	}
}
