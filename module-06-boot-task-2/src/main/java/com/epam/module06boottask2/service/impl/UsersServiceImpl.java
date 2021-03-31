package com.epam.module06boottask2.service.impl;

import com.epam.module06boottask2.repository.UsersDBQuery;
import com.epam.module06boottask2.security.UsersHelper;
import com.epam.module06boottask2.security.UsersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UserDetailsService {

    @Autowired
    UsersDBQuery usersDBQuery;

    @Override
    public UsersHelper loadUserByUsername(final String username) throws UsernameNotFoundException {
        UsersPojo usersPojo = null;
        try {
            usersPojo = usersDBQuery.getUserDetails(username);
            UsersHelper userDetail = new UsersHelper(usersPojo);
            return userDetail;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }

}
