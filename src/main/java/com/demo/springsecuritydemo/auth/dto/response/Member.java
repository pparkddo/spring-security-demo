package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.entity.User;
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
public class Member {

    private String nickname;
    private String email;
    private String imageUrl;

    public static Member of(User user) {
        return Member.builder().nickname(user.getNickname()).email(user.getEmail())
            .imageUrl(user.getImageUrl()).build();
    }
}
