package com.kgcorner.dto;

public class Login extends BaseModel {

    private String userName;
    private String password;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static String getUserNameKeyName() {
        return "userName";
    }
}
