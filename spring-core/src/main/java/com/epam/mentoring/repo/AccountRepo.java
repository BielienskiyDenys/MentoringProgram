package com.epam.mentoring.repo;

import com.epam.mentoring.model.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepo extends MongoRepository<UserAccount, Long> {
}
