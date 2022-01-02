package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;
import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
            .emailVerified(user.isEmailVerified()).provider(user.getProvider()).authorities(
                authorities.stream().map(AuthorityResponse::of).collect(Collectors.toList()))
            .build();
    }
}
