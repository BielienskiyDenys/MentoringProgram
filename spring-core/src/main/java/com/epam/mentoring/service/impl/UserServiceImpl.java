package com.epam.mentoring.service.impl;

import com.epam.mentoring.model.User;
import com.epam.mentoring.model.UserAccount;
import com.epam.mentoring.repo.AccountRepo;
import com.epam.mentoring.repo.UserRepo;
import com.epam.mentoring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private UserRepo userRepo;
	private AccountRepo accountRepo;

	public UserServiceImpl(UserRepo userRepo, AccountRepo accountRepo) {
		this.userRepo = userRepo;
		this.accountRepo = accountRepo;
	}

	@Override
	@Transactional
	public boolean addUser(User user) {
		if( validateUserFields(user) && !userRepo.findById(user.getId()).isPresent()  && userRepo.findByEmail(user.getEmail()).isEmpty() ) {
			logger.debug("addUser({}) call. Readressing to repository.", user);
			user = userRepo.save(user);
			UserAccount account = new UserAccount(user);
			accountRepo.save(account);
			return true;
		}
		logger.error("addUser({}) call. Failed to register new user.", user);
		return false;
	}

	//Не знаю, стоит ли тут добавлять транзакцию
	@Override
	public boolean removeUserById(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			logger.debug("removeUserById({}) call. Readressing to repository.", userId);
			userRepo.deleteById(userId);
			return true;
		}
		logger.error("removeUserById({}) call. No user found.", userId);
		return false;
	}

	@Override
	public User findUserById(long userId) {
		logger.debug("findUserById({}) call. Readressing to repository.", userId);
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			logger.error("findUserById({}) call. No user found.", userId);
			User newUser = new User();
			newUser.setName("No such user");
			return newUser;
		}
		return user.get();
	}

	@Override
	public User findUserByEmail(String userEmail) {
		logger.debug("findUserByEmail({}) call. Readressing to repository.", userEmail);
		List<User> resultList = userRepo.findByEmail(userEmail);
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}
		User user = new User();
		user.setName("No such user");
		logger.error("findUserByEmail({}) call. No user found.", userEmail);
		return user;
	}

	@Override
	public List<User> findUsersByName(String userName) {
		logger.debug("findUsersByName({}) call. Readressing to repository.", userName);
		return userRepo.findByNameContaining(userName);
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {
		if (validateUserFields(user) && userRepo.findById(user.getId()).isPresent()) {
			logger.debug("updateUser({}) call. Readressing to repository.", user);
			userRepo.save(user);
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
