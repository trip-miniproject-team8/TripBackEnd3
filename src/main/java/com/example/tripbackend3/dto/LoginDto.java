package com.example.tripbackend3.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class LoginDto implements UserDetails {
    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 계정 만료 여부(true: 만료되지 않음, false: 만료됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠금 여부(true: 계정잠금아님, false: 계정잠금상태)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 계정 패스워드 만료 여부(true: 만료되지 않음, false: 만료됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정 사용가능 여부(true: 사용가능, false: 사용불가능)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
