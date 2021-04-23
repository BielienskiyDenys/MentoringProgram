package com.epam.mentoring.service;

import com.epam.mentoring.exception.EntityNotFoundException;
import com.epam.mentoring.model.UserAccount;

import java.util.Optional;

public interface AccountService {
    public UserAccount charge(Long id, Double amount) throws EntityNotFoundException;
    public boolean chargeOff(Long id, Double amount) throws EntityNotFoundException;
    public Double checkBalance(Long id);
}
