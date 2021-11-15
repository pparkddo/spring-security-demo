package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;
import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailMember {

    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String imageUrl;
    private boolean emailVerified;
    private AuthProvider provider;
    private List<AuthorityResponse> authorities;

    public static DetailMember of(User user, List<Authority> authorities) {
        return DetailMember.builder().id(user.getId()).name(user.getName())
            .nickname(user.getNickname()).email(user.getEmail()).imageUrl(user.getImageUrl())
            .emailVerified(user.isEmailVerified()).authProvider(user.getProvider()).authorities(
                authorities.stream().map(AuthorityResponse::of).collect(Collectors.toList()))
            .build();
    }

    @Builder
    private DetailMember(Long id, String name, String nickname, String email, String imageUrl,
        boolean emailVerified, AuthProvider authProvider, List<AuthorityResponse> authorities) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.emailVerified = emailVerified;
        this.provider = authProvider;
        this.authorities = authorities;
    }
}
