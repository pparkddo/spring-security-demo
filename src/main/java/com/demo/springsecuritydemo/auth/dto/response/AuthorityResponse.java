package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.entity.Authority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityResponse {

    private String roleName;

    public static AuthorityResponse of(Authority authority) {
        return AuthorityResponse.builder().roleName(authority.getRole().name()).build();
    }

    @Builder
    private AuthorityResponse(String roleName) {
        this.roleName = roleName;
    }
}
