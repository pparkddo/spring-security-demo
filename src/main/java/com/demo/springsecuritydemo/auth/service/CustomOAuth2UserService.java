package com.demo.springsecuritydemo.auth.service;

import com.demo.springsecuritydemo.auth.constant.AuthProvider;
import com.demo.springsecuritydemo.auth.constant.Role;
import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import com.demo.springsecuritydemo.auth.exception.OAuth2ProcessingException;
import com.demo.springsecuritydemo.auth.factory.OAuth2UserInfoFactory;
import com.demo.springsecuritydemo.auth.model.OAuth2UserInfo;
import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import com.demo.springsecuritydemo.auth.repository.AuthorityRepository;
import com.demo.springsecuritydemo.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        AuthProvider authProvider = AuthProvider.fromRegistrationId(userRequest.getClientRegistration().getRegistrationId());
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        String email = oAuth2UserInfo.getEmail();
        if (!StringUtils.hasText(email)) {
            throw new OAuth2ProcessingException("Email not found from OAuth2 User Information");
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(oAuth2UserInfo));
        validateExistingUser(user, oAuth2UserInfo.getProvider());
        updateExistingUser(user, oAuth2UserInfo);

        return UserPrincipal.of(user, authorityRepository.findByUser(user), oAuth2User.getAttributes());
    }

    private void validateExistingUser(User user, AuthProvider authProvider) {
        if (user.getProvider() != authProvider) {
            throw new OAuth2ProcessingException(
                    "Already singed up with " + user.getProvider()
                            + ", Please use your " + authProvider + "account to login");
        }
    }

    private void updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.changeName(oAuth2UserInfo.getName());
        user.changeImageUrl(oAuth2UserInfo.getImageUrl());
        userRepository.save(user);
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .name(oAuth2UserInfo.getName())
                .nickname(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .emailVerified(oAuth2UserInfo.isEmailVerified())
                .authProvider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getId())
                .build();
        userRepository.save(user);

        Authority authority = Authority.builder().user(user).role(Role.USER).build();
        authorityRepository.save(authority);

        return user;
    }
}
