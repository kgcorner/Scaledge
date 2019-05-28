package com.kgcorner.scaledgeauth.service;


import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.util.GsonUtil;
import com.kgcorner.scaledge.util.JwtUtility;
import com.kgcorner.scaledge.util.Strings;
import com.kgcorner.scaledge.util.Utility;
import com.kgcorner.scaledgeauth.ApplicationProperties;
import com.kgcorner.scaledgeauth.dao.entity.Login;
import com.kgcorner.scaledgeauth.dao.repo.LoginDataRepo;
import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgeauth.exception.InvalidRefreshTokenException;
import com.kgcorner.scaledgeauth.exception.TokenVerificationFailedException;
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
    private LoginDataRepo dataRepo;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private LoginDataRepo loginRepository;

    /**
     * Returns @{@link TokenDto} if token is valid
     * @param token basic token including basic
     * @return TokenDto object containing bearer and refresh token
     * @throws AuthenticationFailedException if token contains invalid username and password
     */
    public TokenDto getToken(String token) throws AuthenticationFailedException {
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
        Login login = dataRepo.getByKey(Login.USER_NAME, userName, Login.class);
        if(login == null || !Strings.isHashMatching(password, login.getPassword()))
            throw new AuthenticationFailedException();
        String secret = properties.getAuthSecretKey();
        int expiresInSecond = properties.getBearerTokenExpiresInSecond();
        int refreshTokenLength = properties.getRefreshTokenLength();
        UserPreview user = login.getUser();
        String userJson = GsonUtil.getGson().toJson(user);
        Map<String, String> claims = new HashMap<>();
        claims.put(USER_CLAIM_KEY, userJson);
        String jwtToken = JwtUtility.createJWTToken(secret, claims, expiresInSecond);
        String refreshToken = Strings.generateRandomString(refreshTokenLength);
        TokenDto tokenDtoObject = new TokenDto( jwtToken, refreshToken, Utility.getTimeAfterSeconds(expiresInSecond));
        login.setRefreshToken(refreshToken);
        dataRepo.update(login);
        return tokenDtoObject;
    }

    /**
     * Validates given JWT and returns user embedded in the token
     * @param token jwt token
     * @return user embedded in the token
     * @throws TokenVerificationFailedException if token verification fails
     */
    public UserPreview validateJwt(String token) throws TokenVerificationFailedException {
        if(!token.toLowerCase().startsWith(BEARER_TOKEN_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("invalid token provided");
        }
        token = token.substring(BEARER_TOKEN_TYPE.length());
        String secret = properties.getAuthSecretKey();
        if(JwtUtility.validateToken(secret, token)) {
            String userClaim = JwtUtility.getClaim(USER_CLAIM_KEY, token);
            return GsonUtil.getGson().fromJson(userClaim, UserPreview.class);
        } else {
            throw new TokenVerificationFailedException();
        }
    }

    /**
     * Refreshes given token by validating refresh token for given user
     * @param refreshToken refresh token
     * @return newly created {@link TokenDto}
     * @throws InvalidRefreshTokenException if refresh token is not associated with any user
     */
    public TokenDto refreshToken(String refreshToken) throws InvalidRefreshTokenException {
        Login login = dataRepo.getByKey(Login.REFRESH_TOKEN, refreshToken, Login.class);
        if(login == null) {
            throw new InvalidRefreshTokenException();
        }
        String secret = properties.getAuthSecretKey();
        int expiresInSecond = properties.getBearerTokenExpiresInSecond();
        int refreshTokenLength = properties.getRefreshTokenLength();
        Map<String, String> claims = new HashMap<>();
        claims.put(USER_CLAIM_KEY, GsonUtil.getGson().toJson(login.getUser()));
        String jwtToken = JwtUtility.createJWTToken(secret, claims, expiresInSecond);
        refreshToken = Strings.generateRandomString(refreshTokenLength);
        login.setRefreshToken(refreshToken);
        dataRepo.update(login);
        TokenDto tokenDtoObject = new TokenDto( jwtToken, refreshToken, Utility.getTimeAfterSeconds(expiresInSecond));
        return tokenDtoObject;
    }

    /**
     * Register and returns a new login
     * @param login {@link Login} object
     * @return created login object
     */
    public Login registerNewLogin(Login login) {
        if(!login.isValid()) {
            throw new IllegalArgumentException("data is not valid");
        }
        login.setPassword(Strings.getHash(login.getPassword()));
        return dataRepo.create(login);
    }

}
