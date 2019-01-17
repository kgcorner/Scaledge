package com.kgcorner.scaledgeauth.exception;

public class TokenVerificationFailedException extends Exception {
    public TokenVerificationFailedException() {
        super("Token verification failed");
    }

    public TokenVerificationFailedException(String message) {
        super(message);
    }
}
