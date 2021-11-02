package com.demo.springsecuritydemo.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2ProcessingException extends AuthenticationException {

    public OAuth2ProcessingException(String message) {
        super(message);
    }
}
