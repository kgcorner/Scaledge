package com.kgcorner.scaledge.dto;

/*
Description : <Write is class Description>
Author: kumar
Created on : 27/5/19
*/

import com.kgcorner.scaledge.previewobjects.UserPreview;

public class LoginDto {
    private String userName;
    private String password;
    private UserPreview user;
    private String refreshToken;
    private String id;

    public static final String USER_NAME = "userName";
    public static final String REFRESH_TOKEN = "refreshToken";


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPreview getUser() {
        return user;
    }

    public void setUser(UserPreview user) {
        this.user = user;
    }



    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}