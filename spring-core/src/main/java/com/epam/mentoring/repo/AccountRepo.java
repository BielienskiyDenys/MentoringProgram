package com.epam.mentoring.repo;

import com.epam.mentoring.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends CrudRepository<UserAccount, Long> {
}
