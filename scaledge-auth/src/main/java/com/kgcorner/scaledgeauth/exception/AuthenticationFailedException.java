package com.kgcorner.scaledgeauth.exception;

public class AuthenticationFailedException extends Exception {

    public AuthenticationFailedException() {
        super("Authentication failed");
    }
}
