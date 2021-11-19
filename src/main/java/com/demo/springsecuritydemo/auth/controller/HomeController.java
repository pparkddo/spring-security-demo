package com.demo.springsecuritydemo.auth.controller;

import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal UserPrincipal user) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", user);
        return mav;
    }
}
