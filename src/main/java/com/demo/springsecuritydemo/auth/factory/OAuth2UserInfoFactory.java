package com.demo.springsecuritydemo.auth.factory;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;
import com.demo.springsecuritydemo.auth.model.GoogleOAuth2UserInfo;
import com.demo.springsecuritydemo.auth.model.OAuth2UserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        if (AuthProvider.GOOGLE == authProvider) {
            return GoogleOAuth2UserInfo.builder().attributes(attributes).build();
        }
        throw new IllegalArgumentException("Invalid AuthProvider: " + authProvider);
    }
}
