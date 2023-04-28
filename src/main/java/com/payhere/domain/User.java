package com.payhere.domain;

import com.payhere.domain.common.RoleType;
import com.payhere.domain.model.UserDataModel;
import com.payhere.presentation.request.SignUpRequest;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.valueOf;

@NoArgsConstructor
public final class User {
    private String phoneNumber;
    private String password;
    private Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    public User(SignUpRequest signUpRequest, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.phoneNumber = signUpRequest.getPhoneNumber();
        this.password = bCryptPasswordEncoder.encode(signUpRequest.getPassword());
    }
    public UserDataModel toEntity() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(valueOf(RoleType.ROLE_USER)));
        return new UserDataModel(this.phoneNumber, this.password, authorities);
    }
}
