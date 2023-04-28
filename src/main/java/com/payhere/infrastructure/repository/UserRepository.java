package com.payhere.infrastructure.repository;

import com.payhere.application.IUserRepository;
import com.payhere.domain.User;
import com.payhere.domain.model.UserDataModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final UserJpaRepository userJpaRepository;
    @Override
    public UserDataModel save(UserDataModel userDataModel) {
        return userJpaRepository.save(userDataModel);
    }

    @Override
    public UserDataModel find(String phoneNumber) {
        return userJpaRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(EntityNotFoundException::new);
    }
}
