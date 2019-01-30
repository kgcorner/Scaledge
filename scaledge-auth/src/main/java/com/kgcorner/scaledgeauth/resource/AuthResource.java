package com.kgcorner.scaledgeauth.resource;

import com.kgcorner.dto.Token;
import com.kgcorner.dto.User;
import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgeauth.exception.InvalidRefreshTokenException;
import com.kgcorner.scaledgeauth.exception.TokenVerificationFailedException;
import com.kgcorner.scaledgeauth.service.AuthenticationService;
import com.kgcorner.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource extends ExceptionAware {
    static final String AUTHORIZATION_TOKEN = "Authorization";
    static final String REFRESH_TOKEN = "refresh-token";

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Validates login and generates a bearer token
     * @param authorizationToken
     * @return
     */
    @GetMapping(value = "/login", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    Token login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) throws AuthenticationFailedException {
        return authenticationService.getToken(authorizationToken);
    }

    /**
     * Validates bearer token given by a user
     * @param authorizationToken
     * @return User holding the bearer token
     */
    @GetMapping(value = "/validates", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    User validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken)
            throws TokenVerificationFailedException {
        return authenticationService.validateJwt(authorizationToken);
    }

    /**
     * Refreshes bearer token for a user
     * @param refreshToken
     * @return
     */
    @GetMapping(value = "/refresh", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    Token refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken) throws InvalidRefreshTokenException {
        return authenticationService.refreshToken(refreshToken);
    }
}
