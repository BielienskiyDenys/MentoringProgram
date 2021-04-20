package com.epam.mentoring.service.impl;

import com.epam.mentoring.model.UserAccount;
import com.epam.mentoring.repo.AccountRepo;
import com.epam.mentoring.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    @Transactional
    public boolean charge(Long id, Double amount) {
        Optional<UserAccount> accountOpt = accountRepo.findById(id);
        if (accountOpt.isPresent()) {
            logger.info("Charging balance for user {} for amount = {}", id, amount);
            UserAccount account = accountOpt.get();
            account.setBalance(account.getBalance() + amount);
            accountRepo.save(account);
            return true;
        }
        logger.error("Charging balance for user {} for amount = {} failed. User not found.", id, amount);
        return false;
    }

    @Override
    @Transactional
    public boolean chargeOff(Long id, Double amount) {
        Optional<UserAccount> accountOpt = accountRepo.findById(id);
        if (accountOpt.isPresent() && accountOpt.get().getBalance() >=  amount) {
            logger.info("Charging off balance for user {} for amount = {}", id, amount);
            UserAccount account = accountOpt.get();
            account.setBalance(account.getBalance() - amount);
            accountRepo.save(account);
            return true;
        }
        logger.error("Charging balance for user {} for amount = {} failed. User not found or insufficient funds..", id, amount);
        return false;
    }

    @Override
    public Double checkBalance(Long id) {
        Optional<UserAccount> accountOpt = accountRepo.findById(id);
        if (accountOpt.isPresent()) {
            logger.info("Checking balance for user {}", id);
            return accountOpt.get().getBalance();
        }
        logger.info("Checking balance for user {} failed. No user found.", id);
        return 0.0;
    }
}
