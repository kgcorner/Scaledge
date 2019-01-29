package com.kgcorner.scaledge.clients;

import com.kgcorner.models.Token;
import com.kgcorner.models.User;
import com.kgcorner.util.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Client for Sclaedge Authentication service
 */
@FeignClient("scaledge-auth")
public interface AuthenticationClient {
    static final String AUTHORIZATION_TOKEN = "Authorization";
    static final String REFRESH_TOKEN = "refresh-token";

    /**
     * Validates login using user's credentials
     * @param authorizationToken
     * @return
     */
    @GetMapping(value = "/login", produces = Constants.PRODUCES_APPLICATION_JSON)
    Token login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken);

    /**
     * Validates the JWT token
     * @param authorizationToken
     * @return
     */
    @GetMapping(value = "/validates", produces = Constants.PRODUCES_APPLICATION_JSON)
    User validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken);

    /**
     * Refreshes the jwt token
     * @param refreshToken
     * @return
     */
    @GetMapping(value = "/refresh", produces = Constants.PRODUCES_APPLICATION_JSON)
    Token refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken);
}
