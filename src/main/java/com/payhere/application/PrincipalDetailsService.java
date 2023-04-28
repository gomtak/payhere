package com.payhere.application;

import com.payhere.domain.PrincipalDetails;
import com.payhere.domain.model.UserDataModel;
import com.payhere.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserDataModel user = userRepository.find(phoneNumber);
        return new PrincipalDetails(user);
    }
}
