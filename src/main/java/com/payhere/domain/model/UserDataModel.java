package com.payhere.domain.model;

import com.payhere.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class UserDataModel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "user_id"))
    private Set<SimpleGrantedAuthority> roles;
    public UserDataModel(String phoneNumber, String password, Set<SimpleGrantedAuthority> authorities) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = authorities;
    }
}
