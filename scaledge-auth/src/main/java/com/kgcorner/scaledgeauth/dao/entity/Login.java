package com.kgcorner.scaledgeauth.dao.entity;


import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.util.Strings;
import com.kgcorner.scaledgedata.models.BaseModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Login extends BaseModel {

    private String userName;
    private String password;
    private UserPreview user;
    private String refreshToken;
    private String role;

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

    public String getRole() {
        if(Strings.isNullOrEmpty(role))
            return "user";
        return role;
    }

    public void setRole(String role) {
        if(Strings.isNullOrEmpty(role))
            this.role = "user";
        this.role = role;
    }
}
