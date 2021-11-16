package com.demo.springsecuritydemo.auth.model;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    public UserAuthenticationToken(UserPrincipal userPrincipal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userPrincipal = userPrincipal;
        this.setAuthenticated(true);
    }

    /**
     * UserAuthenticationToken does not store credentials
     * @return empty string
     */
    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;
    }
}
