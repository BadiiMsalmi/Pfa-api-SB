package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    Optional<Object> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email OR u.id = :id")
    Optional<UserEntity> findByEmailOrId(@Param("email") String email, @Param("id") Integer id);
}