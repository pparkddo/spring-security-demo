package com.demo.springsecuritydemo.auth.controller;

import com.demo.springsecuritydemo.auth.dto.request.ChangedAuthority;
import com.demo.springsecuritydemo.auth.dto.response.AuthorityResponse;
import com.demo.springsecuritydemo.auth.dto.response.DetailMember;
import com.demo.springsecuritydemo.auth.dto.response.Member;
import com.demo.springsecuritydemo.auth.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String index() {
        return "admin";
    }

    @ResponseBody
    @GetMapping("/member")
    public List<Member> getMembers() {
        return adminService.getMembers();
    }

    @ResponseBody
    @GetMapping("/member/{memberId}")
    public DetailMember getDetailMember(@PathVariable Long memberId) {
        return adminService.getDetailMember(memberId);
    }

    @ResponseBody
    @PostMapping("/member/{memberId}")
    public List<AuthorityResponse> changeRole(@PathVariable Long memberId,
        @RequestBody List<ChangedAuthority> changedAuthorities) {
        return adminService.changeRole(memberId, changedAuthorities);
    }

    @ResponseBody
    @DeleteMapping(value = "/member/{memberId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public boolean deleteMember(@PathVariable Long memberId) {
        adminService.deleteMember(memberId);
        return true;
    }
}
