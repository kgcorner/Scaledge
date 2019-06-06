package com.kgcorner.scaledge.dto;

import java.util.Date;

public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Date expiresOn;

    public TokenDto(String jwtToken, String refreshToken, Date expiresOn) {
        this.accessToken = jwtToken;
        this.refreshToken = refreshToken;
        this.expiresOn = expiresOn;
    }

    public TokenDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }
}
