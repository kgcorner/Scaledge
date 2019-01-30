package com.kgcorner.scaledgeauth.service;

import com.kgcorner.dto.Login;
import com.kgcorner.dto.Token;
import com.kgcorner.dto.User;
import com.kgcorner.scaledgeauth.ApplicationProperties;
import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgeauth.exception.InvalidRefreshTokenException;
import com.kgcorner.scaledgeauth.exception.TokenVerificationFailedException;
import com.kgcorner.scaledgedata.dao.ScaledgeRepository;
import com.kgcorner.util.GsonUtil;
import com.kgcorner.util.JwtUtility;
import com.kgcorner.util.Strings;
import com.kgcorner.util.Utility;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private static final String USER_CLAIM_KEY = "user";
    private static final String BEARER_TOKEN_TYPE = "Bearer ";
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class);
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
        Login login = loginRepository.getByKey(Login.getUserNameKeyName(), userName, Login.class);
        if(login == null) {
            throw new AuthenticationFailedException();
        }
        if(!login.getPassword().equals(password)) {
            throw new AuthenticationFailedException();
        }
        String secret = properties.getAuthSecretKey();
        int expiresInSecond = properties.getBearerTokenExpiresInSecond();
        int refreshTokenLength = properties.getRefreshTokenLength();
        User userPreview = login.getUser();
        String userJson = GsonUtil.getGson().toJson(userPreview);
        Map<String, String> claims = new HashMap<>();
        claims.put(USER_CLAIM_KEY, userJson);
        String jwtToken = JwtUtility.createJWTToken(secret, claims, expiresInSecond);
        String refreshToken = Strings.generateRandomString(refreshTokenLength);
        User user = userRepository.getById(userPreview.getId(), User.class);
        if(user == null) {
            LOGGER.warn("No user associated with login:username" + userName);
            throw new AuthenticationFailedException();
        }
        user.setRefreshToken(refreshToken);
        userRepository.update(user);
        Token tokenObject = new Token( jwtToken, refreshToken, Utility.getTimeAfterSeconds(expiresInSecond));
        return tokenObject;
    }

    /**
     * Validates given JWT and returns user embedded in the token
     * @param token jwt token
     * @return user embedded in the token
     * @throws TokenVerificationFailedException if token verification fails
     */
    public User validateJwt(String token) throws TokenVerificationFailedException {
        if(!token.toLowerCase().startsWith(BEARER_TOKEN_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("invalid token provided");
        }
        token = token.substring(BEARER_TOKEN_TYPE.length());
        String secret = properties.getAuthSecretKey();
        if(JwtUtility.validateToken(secret, token)) {
            String userClaim = JwtUtility.getClaim(USER_CLAIM_KEY, token);
            return GsonUtil.getGson().fromJson(userClaim, User.class);
        } else {
            throw new TokenVerificationFailedException();
        }
    }

    /**
     * Refreshes given token by validating refresh token for given user
     * @param refreshToken refresh token
     * @return newly created {@link Token}
     * @throws InvalidRefreshTokenException if refresh token is not associated with any user
     */
    public Token refreshToken(String refreshToken) throws InvalidRefreshTokenException {
        User user = userRepository.getByKey("refreshToken", refreshToken, User.class);
        if(user == null) {
            throw new InvalidRefreshTokenException();
        }
        String secret = properties.getAuthSecretKey();
        int expiresInSecond = properties.getBearerTokenExpiresInSecond();
        int refreshTokenLength = properties.getRefreshTokenLength();
        Map<String, String> claims = new HashMap<>();
        claims.put(USER_CLAIM_KEY, GsonUtil.getGson().toJson(user.extractUserPreview()));
        String jwtToken = JwtUtility.createJWTToken(secret, claims, expiresInSecond);
        refreshToken = Strings.generateRandomString(refreshTokenLength);
        user.setRefreshToken(refreshToken);
        userRepository.update(user);
        Token tokenObject = new Token( jwtToken, refreshToken, Utility.getTimeAfterSeconds(expiresInSecond));
        return tokenObject;
    }


}
