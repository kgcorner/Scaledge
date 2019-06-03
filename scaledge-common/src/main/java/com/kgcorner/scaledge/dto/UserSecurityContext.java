package com.kgcorner.scaledge.dto;

/*
Description : Defines users security context
Author: kumar
Created on : 2/6/19
*/

import com.kgcorner.scaledge.previewobjects.UserPreview;

public class UserSecurityContext {
    private final UserPreview user;
    private final String role;

    public UserSecurityContext(UserPreview user, String role) {
        this.user = user;
        this.role = role;
    }

    public UserPreview getUser() {
        return user;
    }

    public String getRole() {
        return "ROLE_"+role;
    }
}