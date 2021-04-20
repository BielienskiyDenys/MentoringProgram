package com.epam.mentoring.repo;

import com.epam.mentoring.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<UserAccount, Long> {
}
