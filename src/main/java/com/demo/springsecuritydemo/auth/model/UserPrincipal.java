package com.demo.springsecuritydemo.auth.model;

import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final String password;
    private final Collection<CustomAuthority> authorities;
    private final Map<String, Object> attributes;

    public static UserPrincipal of(User user) {
        return UserPrincipal.of(user, Collections.emptyList(), Collections.emptyMap());
    }

    public static UserPrincipal of(User user, Collection<Authority> authorities) {
        return UserPrincipal.of(user, authorities, Collections.emptyMap());
    }

    public static UserPrincipal of(User user, Collection<Authority> authorities, Map<String, Object> attributes) {
        return UserPrincipal.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities.stream().map(CustomAuthority::of).collect(Collectors.toUnmodifiableList()))
                .attributes(attributes)
                .build();
    }

    /**
     * getUsername is used to identify user
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    private UserPrincipal(Long id, String name, String nickname, String email, String password,
                          Collection<CustomAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }
}
