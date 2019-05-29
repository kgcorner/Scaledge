package com.kgcorner.scaledge.resources.auth;


import com.kgcorner.scaledge.clients.UserServiceClient;
import com.kgcorner.scaledge.dto.LoginDto;
import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.dto.UserDto;
import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.services.AuthService;
import com.kgcorner.scaledge.services.UserService;
import com.kgcorner.scaledge.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationResources {
    static final String AUTHORIZATION_TOKEN = "Authorization";
    static final String REFRESH_TOKEN = "refresh-token";

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    public AuthenticationResources() {
        System.out.println("***********************************************");
        System.out.println("*Initializing Rest Controller*");
        System.out.println("***********************************************");
    }

    /**
     * Validates login and generates a bearer token
     * @param authorizationToken
     * @return
     */
    @GetMapping(value = "/login", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    TokenDto login(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) {
        return authService.login(authorizationToken);
    }

    /**
     * Validates bearer token given by a user
     * @param authorizationToken
     * @return User holding the bearer token
     */
    @GetMapping(value = "/validates", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    UserDto validateUser(@RequestHeader(AUTHORIZATION_TOKEN) String authorizationToken) {
        UserPreview userPreview = authService.validateJwt(authorizationToken);
        UserDto user = new UserDto();
        user.setId(userPreview.getId());
        user.setName(userPreview.getName());
        return user;
    }

    /**
     * Refreshes bearer token for a user
     * @param refreshToken
     * @return
     */
    @GetMapping(value = "/refresh", produces = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    TokenDto refreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    /**
     * Register
     * @param login
     * @return
     */
    @PostMapping(value = "/register", produces = Constants.PRODUCES_APPLICATION_JSON,
        consumes = Constants.PRODUCES_APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    LoginDto registerLogin(@RequestBody LoginDto login) {
        if(login.isValid()) {
            UserDto user = new UserDto();
            user.setName(login.getUser().getName());
            userService.registerUser(user);
            login.getUser().setId(user.getId());
            login = authService.registerNewLogin(login);
            login.setPassword("");
            return  login;
        } else {
            throw new IllegalArgumentException("invalid entry provided");
        }



    }
}
