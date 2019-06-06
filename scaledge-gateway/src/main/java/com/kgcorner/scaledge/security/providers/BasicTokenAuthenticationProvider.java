package com.kgcorner.scaledge.security.providers;

/*
Description : Authentication provider for Basic Token
Author: kumar
Created on : 1/6/19
*/

import com.kgcorner.scaledge.dto.TokenDto;
import com.kgcorner.scaledge.dto.UserSecurityContext;
import com.kgcorner.scaledge.security.tokens.BasicToken;
import com.kgcorner.scaledge.services.AuthService;
import com.kgcorner.scaledge.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class BasicTokenAuthenticationProvider implements AuthenticationProvider {

    private static final String USER_CLAIM_KEY = "user";

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicToken token = (BasicToken) authentication;
        String userName = token.getPrincipal().toString();
        String basicToken = token.getBasicToken();
        TokenDto tokenDto = authService.login(basicToken);
        UserSecurityContext context = JwtUtility.getClaim(USER_CLAIM_KEY,
            tokenDto.getAccessToken(), UserSecurityContext.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(context.getRole()));
        token = new BasicToken(context, null, authorities);
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BasicToken.class.isAssignableFrom(aClass);
    }
}