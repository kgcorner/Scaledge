package com.kgcorner.scaledge.resources.auth;

/*
Description : Defines endpoints for user resource
Author: kumar
Created on : 31/5/19
*/

import com.kgcorner.scaledge.clients.UserClient;
import com.kgcorner.scaledge.dto.UserDto;
import com.kgcorner.scaledge.services.UserService;
import com.kgcorner.scaledge.util.Constants;
import com.kgcorner.scaledge.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersResources {

    @Autowired
    private UserService service;

    /**
     * Updates user value
     * @param userId
     * @param user
     * @return
     */
    @PutMapping(value = "/users/{userId}", consumes = Constants.PRODUCES_APPLICATION_JSON,
        produces = Constants.PRODUCES_APPLICATION_JSON)
    public UserDto updateUser(@PathVariable("userId") String userId, @RequestBody UserDto user) {
        if(Strings.isNullOrEmpty(userId)) {
            throw new IllegalArgumentException("Invalid UserId");
        }
        return service.updateUser(userId, user);
    }

    /**
     * Returns detail of given user
     * @param userId
     * @return
     */
    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId) {
        if(Strings.isNullOrEmpty(userId)) {
            throw new IllegalArgumentException("Invalid UserId");
        }
        return service.getUser(userId);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return service.getAllUsers();
    }
    @GetMapping(value = "/health", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    String getHealth() {
        return "Healthy";
    }
}