package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    Optional<Object> findByEmail(String email);
}
