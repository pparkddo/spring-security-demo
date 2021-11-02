package com.demo.springsecuritydemo.auth.model;

import com.demo.springsecuritydemo.auth.constant.Role;
import com.demo.springsecuritydemo.auth.entity.Authority;
import lombok.Builder;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
public class CustomAuthority implements GrantedAuthority {

    private final Role role;

    public static CustomAuthority of(Authority authority) {
        return CustomAuthority.builder().role(authority.getRole()).build();
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }

    @Builder
    private CustomAuthority(Role role) {
        this.role = role;
    }
}
