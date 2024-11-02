package com.tekup.pfaapisb.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.pfaapisb.Models.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUserIdAndExpiredIsFalseAndRevokedIsFalse(Long userId);

    Optional<Token> findByToken(String token);


}
