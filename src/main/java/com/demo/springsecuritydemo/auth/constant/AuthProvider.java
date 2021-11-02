package com.demo.springsecuritydemo.auth.constant;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public enum AuthProvider {

    LOCAL("local"),
    GOOGLE("google");

    private final String registrationId;

    public static AuthProvider fromRegistrationId(String registrationId) {
        Objects.requireNonNull(registrationId, "registrationId must not be null");
        for (AuthProvider authProvider : AuthProvider.values()) {
            if (registrationId.equals(authProvider.registrationId)) {
                return authProvider;
            }
        }
        throw new IllegalArgumentException("There is no match AuthProvider, registrationId: " + registrationId);
    }
}
