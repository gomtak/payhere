package com.payhere.application;

import com.payhere.domain.User;
import com.payhere.domain.model.UserDataModel;
import com.payhere.infrastructure.repository.UserRepository;
import com.payhere.presentation.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserDataModel saveUser(SignUpRequest signUpRequest) {
        return userRepository.save(new User(signUpRequest, bCryptPasswordEncoder).toEntity());
    }
}
