package com.epam.mentoring.controller;

import com.epam.mentoring.model.Event;
import com.epam.mentoring.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String checkBalance(@RequestParam(name="user-id") long id, Model model) {
        Double balance = accountService.checkBalance(id);
        logger.info("Returning balance for user {}.", id);
        model.addAttribute("accountsMessage", "User " + id + " has " + balance + " on the account.");
        return "accounts";
    }

    @PostMapping("/add")
    public String addMoney(@RequestParam(name = "user-id") long id,
                              @RequestParam(name = "amount") Double amount,
                              Model model) throws ParseException {
        if(accountService.charge(id, amount)) {
            logger.info("Added {} for user {} balance.", amount, id);
            model.addAttribute("accountsMessage", "Successfully added money.");
        } else {
            logger.error("Failed to add {} money for user {}", amount, id);
            model.addAttribute("accountsMessage", "Failed to add money.");
        }
        return "accounts";
    }


}
