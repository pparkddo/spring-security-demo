package com.demo.springsecuritydemo.auth.controller;

import com.demo.springsecuritydemo.auth.dto.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public ServerResponse<String> index() {
        return ServerResponse.ok("spring-security-demo");
    }
}
