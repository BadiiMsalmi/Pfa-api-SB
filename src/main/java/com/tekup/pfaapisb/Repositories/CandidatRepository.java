package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatRepository extends JpaRepository<Candidat, Integer> {

    Optional<Candidat> findByEmail(String email);

    Candidat findCandidatByEmail(String email);
}
