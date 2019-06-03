package com.kgcorner.scaledge.security.tokens;

/*
Description : <Write class Description>
Author: kumar
Created on : 2/6/19
*/

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class BasicToken extends UsernamePasswordAuthenticationToken {



    public BasicToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BasicToken(Object principal, Object credentials,
                      Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getBasicToken() {
        return "Basic" + Base64.getEncoder().encodeToString((getPrincipal().toString()
                + getCredentials().toString()).getBytes());
    }
}