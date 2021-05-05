package com.epam.mentoring.service.impl;

import com.epam.mentoring.exception.EntityNotFoundException;
import com.epam.mentoring.model.UserAccount;
import com.epam.mentoring.repo.AccountRepo;
import com.epam.mentoring.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;
    private AggregationService aggregationService;

    public AccountServiceImpl(AccountRepo accountRepo, AggregationService aggregationService) {
        this.accountRepo = accountRepo;
        this.aggregationService = aggregationService;
    }

    @Override
    @Transactional
    public UserAccount charge(Long id, Double amount) throws EntityNotFoundException {
        UserAccount account = accountRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("Charging balance for user {} for amount = {}", id, amount);
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
        return account;
    }

    @Override
    @Transactional
    public boolean chargeOff(Long id, Double amount) throws EntityNotFoundException {
        UserAccount account = accountRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if (account.getBalance() >=  amount) {
            logger.info("Charging off balance for user {} for amount = {}", id, amount);
            account.setBalance(account.getBalance() - amount);
            accountRepo.save(account);
            return true;
        }
        logger.error("Charging balance for user {} for amount = {} failed. User not found or insufficient funds..", id, amount);
        return false;
    }

    @Override
    public Double checkBalance(Long id) {
        UserAccount account = accountRepo.findById(id).orElse(new UserAccount());
        logger.info("Checking balance for user {}", id);
        return account.getBalance();
    }
}
