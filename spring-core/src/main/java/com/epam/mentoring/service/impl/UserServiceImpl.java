package com.epam.mentoring.service.impl;

import com.epam.mentoring.dao.UserDao;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.UserImpl;
import com.epam.mentoring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean addUser(User user) {
		if( validateUserFields(user) && userDao.findUserById(user.getId()) == null  && userDao.findUsersByEmail(user.getEmail()).isEmpty() ) {
			logger.debug("addUser({}) call. Readressing to repository.", user);
			userDao.addUser(user);
			return true;
		}
		logger.error("addUser({}) call. Failed to register new user.", user);
		return false;
	}

	public boolean removeUserById(Long userId) {
		User user = userDao.findUserById(userId);
		if (user != null) {
			logger.debug("removeUserById({}) call. Readressing to repository.", userId);
			userDao.removeUserById(userId);
			return true;
		}
		logger.error("removeUserById({}) call. No user found.", userId);
		return false;
	}

	public User findUserById(long userId) {
		logger.debug("findUserById({}) call. Readressing to repository.", userId);
		User user = userDao.findUserById(userId);
		if (user == null) {
			logger.error("findUserById({}) call. No user found.", userId);
			user = new UserImpl();
			user.setName("No such user");
		}
		return user;
	}

	public User findUserByEmail(String userEmail) {
		logger.debug("findUserByEmail({}) call. Readressing to repository.", userEmail);
		List<User> resultList = userDao.findUsersByEmail(userEmail);
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}
		User user = new UserImpl();
		user.setName("No such user");
		logger.error("findUserByEmail({}) call. No user found.", userEmail);
		return user;
	}

	public List<User> findUsersByName(String userName) {
		logger.debug("findUsersByName({}) call. Readressing to repository.", userName);
		return userDao.findUsersByName(userName);
	}

	public boolean updateUser(User user) {
		if (validateUserFields(user) && userDao.findUserById(user.getId()) != null) {
			logger.debug("updateUser({}) call. Readressing to repository.", user);
			userDao.updateUser(user);
			return true;
		}
		logger.error("updateUser({}) call. User not updated. Invalid fields or no such user.", user);
		return false;
	}

	private boolean validateUserFields(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for (ConstraintViolation<User> violation : violations) {
			logger.error(violation.getMessage());
		}
		return violations.isEmpty();
	}
}
