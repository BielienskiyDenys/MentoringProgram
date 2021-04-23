package com.epam.mentoring.repo;

import com.epam.mentoring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
    List<User> findByNameContaining(String email);
}
