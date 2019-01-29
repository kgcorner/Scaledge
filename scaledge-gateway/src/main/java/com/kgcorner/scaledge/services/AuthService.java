package com.kgcorner.scaledge.services;

import com.kgcorner.models.Token;
import com.kgcorner.models.User;
import com.kgcorner.scaledge.clients.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationClient authClient;

    public Token login(String credential) {
        return authClient.login(credential);
    }


    public User validateJwt(String authorizationToken) {
        return authClient.validateUser(authorizationToken);
    }

    public Token refreshToken(String refreshToken) {
       return authClient.refreshToken(refreshToken);
    }
}
