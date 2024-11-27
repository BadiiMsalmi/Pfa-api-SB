package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.OffreEmploi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Integer> {
    Optional<OffreEmploi> findById(Long id);

    void deleteById(Long id);
}
