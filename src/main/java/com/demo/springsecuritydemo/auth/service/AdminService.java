package com.demo.springsecuritydemo.auth.service;

import com.demo.springsecuritydemo.auth.dto.request.ChangedAuthority;
import com.demo.springsecuritydemo.auth.dto.response.AuthorityResponse;
import com.demo.springsecuritydemo.auth.dto.response.DetailMember;
import com.demo.springsecuritydemo.auth.dto.response.Member;
import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import com.demo.springsecuritydemo.auth.repository.AuthorityRepository;
import com.demo.springsecuritydemo.auth.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional(readOnly = true)
    public List<Member> getMembers() {
        return userRepository.findAll().stream().map(Member::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetailMember getDetailMember(Long memberId) {
        User user = userRepository.findById(memberId).orElseThrow();
        return DetailMember.of(user, authorityRepository.findByUser(user));
    }

    @Transactional
    public List<AuthorityResponse> changeRole(Long memberId,
        List<ChangedAuthority> changedAuthorities) {
        User user = userRepository.findById(memberId).orElseThrow();
        authorityRepository.deleteByUser(user);
        List<Authority> authorities = changedAuthorities.stream()
            .map(each -> Authority.builder().user(user).role(each.toRole()).build())
            .collect(Collectors.toList());
        authorityRepository.saveAll(authorities);
        return authorities.stream().map(AuthorityResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        User user = userRepository.findById(memberId).orElseThrow();
        userRepository.delete(user);
    }
}
