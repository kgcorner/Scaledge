package com.kgcorner.scaledge.clients;


import com.kgcorner.scaledge.dto.LoginDto;
import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.dto.UserDto;
import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.util.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    TokenDto login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken);

    /**
     * Validates the JWT token
     * @param authorizationToken
     * @return
     */
    @GetMapping(value = "/validates", produces = Constants.PRODUCES_APPLICATION_JSON)
    UserPreview validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken);

    /**
     * Refreshes the jwt token
     * @param refreshToken
     * @return
     */
    @GetMapping(value = "/refresh", produces = Constants.PRODUCES_APPLICATION_JSON)
    TokenDto refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken);

    @GetMapping(value = "/refresh", produces = Constants.PRODUCES_APPLICATION_JSON,
        consumes = Constants.PRODUCES_APPLICATION_JSON)
    LoginDto registerLogin(@RequestBody LoginDto login);
}
