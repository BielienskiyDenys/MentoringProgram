package com.epam.mentoring.repo;

import com.epam.mentoring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    List<User> findByNameContaining(String email);
}
