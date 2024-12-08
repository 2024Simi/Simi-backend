package com.project.simi.domain.auth.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.simi.domain.auth.enums.AuthoriryEnum;
import com.project.simi.domain.auth.enums.UserStatusEnum;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.dto.RequestUser;

public class AuthenticatedUser implements UserDetails, RequestUser {

    private final transient User user;

    public AuthenticatedUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<AuthoriryEnum> authorities = user.getAuthorities();
        return authorities.stream().map(authority -> (GrantedAuthority) authority::name).toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getStatus().equals(UserStatusEnum.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().equals(UserStatusEnum.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Long getId() {
        return this.user.getId();
    }

    @Override
    public String getLoginId() {
        return this.user.getLoginId();
    }

    @Override
    public String getName() {
        return this.user.getName();
    }
}
