package com.kgcorner.scaledgeauth.service;

import com.kgcorner.models.Login;
import com.kgcorner.models.Token;
import com.kgcorner.models.User;
import com.kgcorner.scaledgeauth.ApplicationProperties;
import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgedata.dao.ScaledgeRepository;
import com.kgcorner.util.GsonUtil;
import com.kgcorner.util.JwtUtility;
import com.kgcorner.util.Strings;
import com.kgcorner.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private ScaledgeRepository<User> userRepository;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private ScaledgeRepository<Login> loginRepository;

    /**
     * Returns @{@link Token} if token is valid
     * @param token basic token including basic
     * @return Token object containing bearer and refresh token
     * @throws AuthenticationFailedException if token contains invalid username and password
     */
    public Token getToken(String token) throws AuthenticationFailedException {
        if(!token.toLowerCase().startsWith("basic ")) {
            throw new IllegalArgumentException("invalid authentication token provided");
        }
        String decoded = new String(Base64.getDecoder().decode(token.substring("basic ".length())));
        if(Strings.isNullOrEmpty(decoded)) {
            throw new IllegalArgumentException("invalid authentication token provided");
        }
        String[] combo = decoded.split(":");
        if(combo.length != 2) {
            throw new IllegalArgumentException("invalid authentication token provided");
        }
        String userName = combo[0];
        String password = combo[1];
        Login login = loginRepository.getByKey(Login.getUserNameKeyName(), userName);

        if(!login.getPassword().equals(password)) {
            throw new AuthenticationFailedException();
        }
        String secret = properties.getAuthSecretKey();
        int expiresInSecond = properties.getBearerTokenExpiresInSecond();
        int refreshTokenLength = properties.getRefreshTokenLength();
        String user = GsonUtil.getGson().toJson(login.getUser());
        Map<String, String> claims = new HashMap<>();
        claims.put("user", user);
        String jwtToken = JwtUtility.createJWTToken(secret, claims, expiresInSecond);
        String refreshToken = Strings.generateRandomString(refreshTokenLength);
        Token tokenObject = new Token( jwtToken, refreshToken, Utility.getTimeAfterSeconds(expiresInSecond));
        return tokenObject;
    }
}
