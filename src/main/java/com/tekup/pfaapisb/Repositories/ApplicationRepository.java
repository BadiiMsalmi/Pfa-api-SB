package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByCandidatId(Long candidatId);

    List<Application> findByOffreIdAndOffreRecruteurEmail(Long offreId, String email);

}
