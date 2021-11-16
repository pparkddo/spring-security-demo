package com.demo.springsecuritydemo.auth.service;

import com.demo.springsecuritydemo.auth.model.UserAuthenticationToken;
import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenService {

    public void updateAuthentication(UserPrincipal userPrincipal) {
        SecurityContextHolder.getContext().setAuthentication(
            new UserAuthenticationToken(userPrincipal, userPrincipal.getAuthorities()));
    }
}
