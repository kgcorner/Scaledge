package com.kgcorner.scaledgeauth.exception;

public class TokenVerificationFailedException extends Exception {
    public TokenVerificationFailedException() {
        super("TokenDto verification failed");
    }
}
