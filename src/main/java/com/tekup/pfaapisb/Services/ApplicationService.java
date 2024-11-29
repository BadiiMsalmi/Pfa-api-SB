package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.Enum.ApplicationStatus;
import com.tekup.pfaapisb.Models.Application;
import com.tekup.pfaapisb.Models.Candidat;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Repositories.ApplicationRepository;
import com.tekup.pfaapisb.Repositories.CandidatRepository;
import com.tekup.pfaapisb.Repositories.OffreEmploiRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final CandidatRepository candidatRepository;

    private final OffreEmploiRepository offreEmploiRepository;

    public final String apply(Long candidatId, Long offreId) {
        Candidat candidat = candidatRepository.findById(Math.toIntExact(candidatId))
                .orElseThrow(() -> new RuntimeException("Candidat not found"));
        OffreEmploi offre = offreEmploiRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("OffreEmploi not found"));

        Application application = new Application();
        application.setCandidat(candidat);
        application.setOffre(offre);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatus(ApplicationStatus.Pending);

        applicationRepository.save(application);

        return "Application submitted successfully!";
    }

    public List<Application> getApplicationsForCandidat(Long candidatId) {
        return applicationRepository.findByCandidatId(candidatId);
    }

    public List<Application> getApplicationsForOffre(Long offreId,String email) {
        List<Application> applications = applicationRepository.findByOffreIdAndOffreRecruteurEmail(offreId,email);
        return applications;
    }

    public void annulerApplication(Long id, String email) {
        Application application = applicationRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (!application.getCandidat().getEmail().equals(email)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à annuler cette candidature.");
        }

        applicationRepository.deleteById(Math.toIntExact(id));
    }

    public void acceptApplication(Long appId,String email) {
        Application application = applicationRepository.findById(Math.toIntExact(appId))
                .orElseThrow(() -> new RuntimeException("Application introuvable"));

        if (!application.getOffre().getRecruteur().getEmail().equals(email)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à accepter cette application.");
        }

        application.setStatus(ApplicationStatus.Accepted);
        applicationRepository.save(application);
    }

    public void refuserApplication(Long appId,String email) {
        Application application = applicationRepository.findById(Math.toIntExact(appId))
                .orElseThrow(() -> new RuntimeException("Application introuvable"));

        if (!application.getOffre().getRecruteur().getEmail().equals(email)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à refuser cette application.");
        }

        application.setStatus(ApplicationStatus.Rejected);
        applicationRepository.save(application);
    }
}
