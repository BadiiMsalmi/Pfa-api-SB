package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruteurRepository extends JpaRepository<Recruteur, Integer> {

    Optional<Recruteur> findByEmail(String email);

    Recruteur findRecruteursByEmail(String email);
}
