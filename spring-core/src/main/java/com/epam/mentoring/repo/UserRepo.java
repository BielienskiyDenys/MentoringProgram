package com.epam.mentoring.repo;

import com.epam.mentoring.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepo extends MongoRepository<User, Long> {
    List<User> findByEmail(String email);
    List<User> findByNameContaining(String email);
}
