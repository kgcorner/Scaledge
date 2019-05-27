package com.kgcorner.scaledgeauth.dao.entity;


import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledgedata.models.BaseModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Login extends BaseModel {

    private String userName;
    private String password;
    private UserPreview user;
    private String refreshToken;

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
}
