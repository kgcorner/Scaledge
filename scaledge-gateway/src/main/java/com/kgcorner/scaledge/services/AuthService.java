package com.kgcorner.scaledge.services;


import com.kgcorner.scaledge.clients.AuthenticationClient;
import com.kgcorner.scaledge.dto.LoginDto;
import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.dto.UserDto;
import com.kgcorner.scaledge.previewobjects.UserPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthenticationClient authClient;

    public TokenDto login(String credential) {
        return authClient.login(credential);
    }


    public UserPreview validateJwt(String authorizationToken) {
        return authClient.validateUser(authorizationToken);
    }

    public TokenDto refreshToken(String refreshToken) {
       return authClient.refreshToken(refreshToken);
    }

    public UserDto registerNewUser(UserDto userDto) {
        return  null;
    }

    public LoginDto registerNewLogin(LoginDto login) {
        return authClient.registerLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
