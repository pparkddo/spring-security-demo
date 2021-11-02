package com.demo.springsecuritydemo.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @RequestMapping("/")
    public String index(@AuthenticationPrincipal OAuth2User user) {
        log.info("####### {}", user);
        return "hi";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/authority")
    public String authority(@AuthenticationPrincipal OAuth2User user) {
        log.info("####### {}", user);
        return "access!";
    }
}
