package com.epam.mentoring.controller;

import com.epam.mentoring.facade.BookingFacade;
import com.epam.mentoring.model.User;
import com.epam.mentoring.model.impl.UserImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {
    private BookingFacade bookingFacade = (new ClassPathXmlApplicationContext("beans.xml")).getBean("bookingFacade", BookingFacade.class);

    @GetMapping("/id")
    public String getUserById(@RequestParam(name="user-id") long userId, Model model) {
        List<User> users = new ArrayList<>();
        User user = bookingFacade.getUserById(userId);
        if (user != null) {
            users.add(user);
        }
        model.addAttribute("usersList", users);
        return "users";
    }

    @GetMapping("/email")
    public String getUserByEmail(@RequestParam(name="email") String email, Model model) {
        List<User> users = new ArrayList<>();
        User user = bookingFacade.getUserByEmail(email);
        if (user != null) {
            users.add(user);
        }
        model.addAttribute("usersList", users);
        return "users";
    }

    @GetMapping("/name")
    public String getUsersByName(@RequestParam(name="name") String name,
                                 @RequestParam(name="page-size", required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(name="page-num", required = false, defaultValue = "0") int pageNum,
                                 Model model) {
        List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);

        model.addAttribute("usersList", users);
        return "users";
    }


    @PostMapping
    public String createUser(@RequestParam(name = "user-id") long userId,
                              @RequestParam(name = "user-name") String userName,
                              @RequestParam(name = "email") String email,
                              Model model) {

        User user = new UserImpl();
        user.setId(userId);
        user.setName(userName);
        user.setEmail(email);
        List<User> users = new ArrayList<>();
        users.add(bookingFacade.createUser(user));
        model.addAttribute("usersList", users);
        return "users";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam(name = "user-id") long userId,
                             @RequestParam(name = "user-name") String userName,
                             @RequestParam(name = "email") String email,
                             Model model) {

        User user = new UserImpl();
        user.setId(userId);
        user.setName(userName);
        user.setEmail(email);
        User newUser = bookingFacade.updateUser(user);

        if(newUser==null) {
            model.addAttribute("usersList", "Failed to update user.");
        } else {
            List<User> users = new ArrayList<>();
            users.add(bookingFacade.updateUser(user));
            model.addAttribute("usersList", users);
        }
        return "users";
    }

    @PostMapping("/delete")
    public String deleteEvent(@RequestParam(name = "user-id") long userId,
                              Model model){
        if(bookingFacade.deleteUser(userId)) {
            model.addAttribute("usersMessage", "Successfully deleted user with id: " + userId);
        } else {
            model.addAttribute("usersMessage", "Failed to delete user with id: " + userId);
        }
        return "users";
    }



}
