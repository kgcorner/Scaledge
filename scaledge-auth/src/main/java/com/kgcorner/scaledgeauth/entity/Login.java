package com.kgcorner.scaledgeauth.entity;

import com.kgcorner.dto.UserPreview;
import com.kgcorner.scaledgedata.models.BaseModel;

public class Login extends BaseModel {

    private String userName;
    private String password;
    private UserPreview user;

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

    public static String getUserNameKeyName() {
        return "userName";
    }
}
