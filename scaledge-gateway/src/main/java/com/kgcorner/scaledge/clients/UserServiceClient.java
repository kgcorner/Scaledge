package com.kgcorner.scaledge.clients;

import com.kgcorner.scaledge.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
Description : <Write is class Description>
Author: kumar
Created on : 28/5/19
*/
@FeignClient("scaledge-user")
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserDto getUser(@PathVariable("userId") String userId);

    @PostMapping("/users")
    UserDto registerUser(@RequestBody UserDto user);

    @PutMapping("/users/{followerId}/follows/{followingId}")
    void followUser(@PathVariable("followerId") String followerId
        , @PathVariable("followingId") String followingId);
}