package com.kgcorner.scaledgeauth.exception;

public class InvalidRefreshTokenException extends Exception {
    public InvalidRefreshTokenException() {
        super("Refresh token is invalid");
    }
}
