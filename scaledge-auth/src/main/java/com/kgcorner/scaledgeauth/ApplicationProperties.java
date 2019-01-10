package com.kgcorner.scaledgeauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value("auth.secret")
    private String authSecretKey;

    @Value("bearer.token.expires")
    private int bearerTokenExpiresInSecond;

    @Value("refresh.token.length")
    private int refreshTokenLength;

    public String getAuthSecretKey() {
        return authSecretKey;
    }

    public void setAuthSecretKey(String authSecretKey) {
        this.authSecretKey = authSecretKey;
    }

    public int getBearerTokenExpiresInSecond() {
        return bearerTokenExpiresInSecond;
    }

    public void setBearerTokenExpiresInSecond(int bearerTokenExpiresInSecond) {
        this.bearerTokenExpiresInSecond = bearerTokenExpiresInSecond;
    }

    public int getRefreshTokenLength() {
        return refreshTokenLength;
    }

    public void setRefreshTokenLength(int refreshTokenLength) {
        this.refreshTokenLength = refreshTokenLength;
    }
}
