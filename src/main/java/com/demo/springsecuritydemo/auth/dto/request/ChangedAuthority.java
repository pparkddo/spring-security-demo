package com.demo.springsecuritydemo.auth.dto.request;

import com.demo.springsecuritydemo.auth.constant.Role;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangedAuthority {

    @NotBlank
    private String roleName;

    public Role toRole() {
        return Role.valueOf(roleName);
    }
}
