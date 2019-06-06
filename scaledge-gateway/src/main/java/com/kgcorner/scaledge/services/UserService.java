package com.kgcorner.scaledge.services;

/*
Description : <Write is class Description>
Author: kumar
Created on : 28/5/19
*/

import com.kgcorner.scaledge.clients.UserClient;
import com.kgcorner.scaledge.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserClient client;

    /**
     * Registers a user
     * @param user
     * @return
     */
    public UserDto registerUser(UserDto user) {
        return client.registerUser(user);
    }

    /**
     * fetches given user
     * @param userID
     * @return
     */
    public UserDto getUser(String userID) {
        return client.getUser(userID);
    }

    public UserDto updateUser(String userId, UserDto user) {
        return client.updateUser(userId, user);
    }

    public List<UserDto> getAllUsers() {
        return client.getAllUser();
    }
}