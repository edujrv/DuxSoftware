package com.duxsoftware.exception;

public class JwtAuthenticationException extends Exception {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
