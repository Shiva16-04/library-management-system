package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmailVerificationCodeRepository extends JpaRepository<UserEmailVerificationCode, Integer> {
    Optional<UserEmailVerificationCode>findByEmail(String email);
}
