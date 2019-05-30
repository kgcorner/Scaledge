package com.kgcorner.scaledge.clients;

/*
Description : <Write is class Description>
Author: kumar
Created on : 30/5/19
*/

import com.kgcorner.scaledge.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("scaledge-users")
public interface UserClient {
    @GetMapping("/users/{userId}")
    UserDto getUser(@PathVariable("userId") String userId);

    @PostMapping("/users")
    UserDto registerUser(@RequestBody UserDto user);

    @PutMapping("/users/{followerId}/follows/{followingId}")
    void followUser(@PathVariable("followerId") String followerId
        , @PathVariable("followingId") String followingId);
}