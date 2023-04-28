package com.payhere.domain;

import com.payhere.domain.model.UserDataModel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class PrincipalDetails implements UserDetails {
    private final UserDataModel userDataModel;

    public PrincipalDetails(UserDataModel user) {
        this.userDataModel = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        this.userDataModel.getRoles().forEach(R -> {
            authorities.add(() -> String.valueOf(R));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return userDataModel.getPhoneNumber();
    }

    @Override
    public String getUsername() {
        return userDataModel.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
