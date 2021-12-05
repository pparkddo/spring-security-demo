package com.demo.springsecuritydemo.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerResponse<T> {

    private boolean success;
    private T data;
    private String errorMessage;

    public static <T> ServerResponse<T> ok(T data) {
        return new ServerResponse<>(true, data, null);
    }
}
