package com.demo.springsecuritydemo.auth.service;

import com.demo.springsecuritydemo.auth.constant.Role;
import com.demo.springsecuritydemo.auth.entity.Authority;
import com.demo.springsecuritydemo.auth.entity.User;
import com.demo.springsecuritydemo.auth.dto.request.ChangedNickname;
import com.demo.springsecuritydemo.auth.model.UserPrincipal;
import com.demo.springsecuritydemo.auth.repository.AuthorityRepository;
import com.demo.springsecuritydemo.auth.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public void changeNickname(UserPrincipal userPrincipal, ChangedNickname changedNickname) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow();
        user.changeName(changedNickname.getNickname());
    }

    @Transactional
    public void grantAdmin(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow();
        if (!isUserAdmin(user)) {
            authorityRepository.save(Authority.builder().user(user).role(Role.ADMIN).build());
        }
    }

    private boolean isUserAdmin(User user) {
        List<Authority> authorities = authorityRepository.findByUser(user);
        return authorities.stream().anyMatch(each -> each.getRole() == Role.ADMIN);
    }
}
