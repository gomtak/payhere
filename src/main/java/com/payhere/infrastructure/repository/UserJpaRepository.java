package com.payhere.infrastructure.repository;

import com.payhere.domain.model.UserDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserDataModel, Long> {
    Optional<UserDataModel> findByPhoneNumber(String phoneNumber);
}
