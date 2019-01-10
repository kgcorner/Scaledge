package com.kgcorner.scaledgeauth.resource;

import com.kgcorner.models.Token;
import com.kgcorner.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource {
    static final String AUTHORIZATION_TOKEN = "Authorization";
    static final String REFRESH_TOKEN = "refresh-token";


    /**
     * Validates login and generates a bearer token
     * @param authorizationToken
     * @return
     */
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    Token login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) {
        return null;
    }

    /**
     * Validates bearer token given by a user
     * @param authorizationToken
     * @return User holding the bearer token
     */
    @GetMapping("/validates")
    @ResponseStatus(HttpStatus.OK)
    User validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) {
        return null;
    }

    /**
     * Refreshes bearer token for a user
     * @param refreshToken
     * @return
     */
    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    Token refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken){
        return null;
    }
}
