package com.kgcorner.scaledgeauth.resource;


import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.util.Constants;
import com.kgcorner.scaledgeauth.dao.entity.Login;
import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgeauth.exception.InvalidRefreshTokenException;
import com.kgcorner.scaledgeauth.exception.TokenVerificationFailedException;
import com.kgcorner.scaledgeauth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    TokenDto login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) throws AuthenticationFailedException {
        return authenticationService.getToken(authorizationToken);
    }

    @GetMapping(value = "/health", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    String getHealth() {
        return "Healthy";
    }

    /**
     * Validates bearer token given by a user
     * @param authorizationToken
     * @return User holding the bearer token
     */
    @GetMapping(value = "/validates", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    UserPreview validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken)
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
    TokenDto refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken) throws InvalidRefreshTokenException {
        return authenticationService.refreshToken(refreshToken);
    }

    /**
     * Register
     * @param login
     * @return
     */
    @PostMapping(value = "/register", produces = Constants.PRODUCES_APPLICATION_JSON,
        consumes = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    Login registerLogin(@RequestBody Login login) {
        login = authenticationService.registerNewLogin(login);
        login.setPassword("");
        return  login;
    }
}
