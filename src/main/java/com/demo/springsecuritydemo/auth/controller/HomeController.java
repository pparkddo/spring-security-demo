package com.demo.springsecuritydemo.auth.controller;

import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @RequestMapping("/")
    public String index(@AuthenticationPrincipal UserPrincipal user) {
        log.info("####### {}", user);
        return "hi";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/authority")
    public String authority(@AuthenticationPrincipal UserPrincipal user) {
        log.info("####### {}", user);
        return "access!";
    }
}
