package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.entity.Authority;
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
public class AuthorityResponse {

    private String roleName;

    public static AuthorityResponse of(Authority authority) {
        return AuthorityResponse.builder().roleName(authority.getRole().name()).build();
    }
}
