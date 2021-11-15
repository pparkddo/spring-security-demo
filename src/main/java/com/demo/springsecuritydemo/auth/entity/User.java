package com.demo.springsecuritydemo.auth.entity;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    private String imageUrl;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public void changeName(String name) {
        this.name = name;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Builder
    private User(String name, String nickname, String email, String imageUrl, boolean emailVerified,
        String password, AuthProvider authProvider, String providerId) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.emailVerified = emailVerified;
        this.password = password;
        this.provider = authProvider;
        this.providerId = providerId;
    }
}
