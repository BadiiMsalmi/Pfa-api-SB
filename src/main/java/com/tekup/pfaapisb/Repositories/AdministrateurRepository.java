package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer> {

    Optional<Administrateur> findByEmail(String email);
}
