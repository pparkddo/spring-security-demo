package com.demo.springsecuritydemo.auth.model;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;

public interface OAuth2UserInfo {

    String getId();

    String getName();

    String getEmail();

    String getImageUrl();

    boolean isEmailVerified();

    AuthProvider getProvider();

}
