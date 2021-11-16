package com.demo.springsecuritydemo.auth.controller;

import com.demo.springsecuritydemo.auth.dto.request.ChangedNickname;
import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import com.demo.springsecuritydemo.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserPrincipal user) {
        return "hello " + user.getNickname();
    }

    @PostMapping("/nickname")
    public String changeNickname(@AuthenticationPrincipal UserPrincipal user,
        @RequestBody ChangedNickname changedNickname) {
        memberService.changeNickname(user, changedNickname);
        return "change nickname";
    }

    @PostMapping("/authority/admin")
    public String grantAdmin(@AuthenticationPrincipal UserPrincipal user) {
        memberService.grantAdmin(user);
        return "Authorized !!!";
    }
}
