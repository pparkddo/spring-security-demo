package com.demo.springsecuritydemo.auth.dto.response;

import com.demo.springsecuritydemo.auth.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private String nickname;
    private String email;
    private String imageUrl;

    public static Member of(User user) {
        return Member.builder().nickname(user.getNickname()).email(user.getEmail())
            .imageUrl(user.getImageUrl()).build();
    }

    @Builder
    private Member(String nickname, String email, String imageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
