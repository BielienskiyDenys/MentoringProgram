package com.epam.mentoring.service;

import com.epam.mentoring.model.UserAccount;

import java.util.Optional;

public interface AccountService {
    public boolean charge(Long id, Double amount);
    public boolean chargeOff(Long id, Double amount);
    public Double checkBalance(Long id);
}
