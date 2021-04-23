package com.epam.mentoring.service.impl;

import com.epam.mentoring.exception.EntityNotFoundException;
import com.epam.mentoring.model.Event;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.UserAccount;
import com.epam.mentoring.repo.AccountRepo;
import com.epam.mentoring.repo.EventRepo;
import com.epam.mentoring.service.AccountService;
import com.epam.mentoring.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepo accountRepo;

    private final long TEST_USER_ID = 1L;
    private final String TEST_USER_NAME = "Test User";
    private final String TEST_USER_EMAIL = "test@email.com";
    private final User testUser = new User(TEST_USER_ID, TEST_USER_NAME, TEST_USER_EMAIL);
    private final UserAccount testUserAccount = new UserAccount(testUser);
    @BeforeEach
    private void init() {
      testUserAccount.setBalance(200.0);
    }

    @Test
    void charge_successful_scenario() throws EntityNotFoundException {
       when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.of(testUserAccount));

       UserAccount receivedAccount = accountService.charge(TEST_USER_ID, 100.0);

       assertEquals(300.0, receivedAccount.getBalance());
       verify(accountRepo, times(1)).findById(TEST_USER_ID);
       verify(accountRepo, times(1)).save(testUserAccount);
    }

    @Test
    void charge_non_existing_user_scenario() {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        EntityNotFoundException receivedException = null;
        try {
            accountService.charge(TEST_USER_ID, 100.0);
        } catch (EntityNotFoundException exception) {
            receivedException = exception;
        }
        assertNotNull(receivedException);
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
        verify(accountRepo, times(0)).save(any(UserAccount.class));
    }

    @Test
    void charge_off_successful_scenario() throws EntityNotFoundException {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.of(testUserAccount));

        Boolean result = accountService.chargeOff(TEST_USER_ID, 100.0);

        assertTrue(result);
        assertEquals(100.0, testUserAccount.getBalance());
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
        verify(accountRepo, times(1)).save(testUserAccount);
    }

    @Test
    void charge_off_non_existing_user_scenario() {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        EntityNotFoundException receivedException = null;
        try {
            accountService.chargeOff(TEST_USER_ID, 100.0);
        } catch (EntityNotFoundException exception) {
            receivedException = exception;
        }
        assertNotNull(receivedException);
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
        verify(accountRepo, times(0)).save(any(UserAccount.class));
    }

    @Test
    void charge_off_more_than_balance_scenario() throws EntityNotFoundException {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.of(testUserAccount));

        Boolean result = accountService.chargeOff(TEST_USER_ID, 300.0);

        assertFalse(result);
        assertEquals(200.0, testUserAccount.getBalance());
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
        verify(accountRepo, times(0)).save(testUserAccount);
    }

    @Test
    void check_balance_existing_user_scenario() {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.of(testUserAccount));

        Double result = accountService.checkBalance(TEST_USER_ID);

        assertEquals(200.0, result);
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
    }

    @Test
    void check_balance_non_existing_user_scenario() {
        when(accountRepo.findById(TEST_USER_ID)).thenReturn(Optional.empty());

        Double result = accountService.checkBalance(TEST_USER_ID);

        assertEquals(0.0, result);
        verify(accountRepo, times(1)).findById(TEST_USER_ID);
    }
}
