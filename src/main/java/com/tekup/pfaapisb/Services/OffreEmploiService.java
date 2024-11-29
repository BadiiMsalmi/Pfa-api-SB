package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.OffreEmploiIDTO;
import com.tekup.pfaapisb.Models.Competence;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Models.Recruteur;
import com.tekup.pfaapisb.Repositories.CompetenceRepository;
import com.tekup.pfaapisb.Repositories.OffreEmploiRepository;
import com.tekup.pfaapisb.Repositories.RecruteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OffreEmploiService {


    private final OffreEmploiRepository offreEmploiRepository;
    private final RecruteurRepository recruteurRepository;
    private final CompetenceRepository competenceRepository;

    public OffreEmploi createOffre(OffreEmploiIDTO offreEmploiDTO) {
        OffreEmploi offreEmploi = new OffreEmploi();
        Recruteur recruteur = recruteurRepository.findRecruteurById(offreEmploiDTO.getRecruteurId());
        List<Long> competenceIds = offreEmploiDTO.getCompetences();
        List<Competence> competences = competenceRepository.findAllById(competenceIds);
        offreEmploi.setTitre(offreEmploiDTO.getTitre());
        offreEmploi.setDescription(offreEmploiDTO.getDescription());
        offreEmploi.setCompetences(competences);
        offreEmploi.setExperience(offreEmploiDTO.getExperience());
        offreEmploi.setLocalisation(offreEmploiDTO.getLocalisation());
        offreEmploi.setSalaire(offreEmploiDTO.getSalaire());
        offreEmploi.setRecruteur(recruteur);
        return offreEmploiRepository.save(offreEmploi);
    }

    public OffreEmploi updateOffre(Long id, OffreEmploiIDTO offreEmploiDTO, String email) {
        OffreEmploi offreEmploi = offreEmploiRepository.findById(id).orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        // Check if the recruiter owns the offer
        if (!offreEmploi.getRecruteur().getEmail().equals(email)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette offre.");
        }
        List<Long> competenceIds = offreEmploiDTO.getCompetences();
        List<Competence> competences = competenceRepository.findAllById(competenceIds);
        offreEmploi.setTitre(offreEmploiDTO.getTitre());
        offreEmploi.setDescription(offreEmploiDTO.getDescription());
        offreEmploi.setCompetences(competences);
        offreEmploi.setExperience(offreEmploiDTO.getExperience());
        offreEmploi.setLocalisation(offreEmploiDTO.getLocalisation());
        offreEmploi.setSalaire(offreEmploiDTO.getSalaire());
        return offreEmploiRepository.save(offreEmploi);
    }

    @Transactional
    public void deleteOffreById(Long id, String email,Authentication authentication) {
        OffreEmploi offreEmploi = offreEmploiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        // Check if the recruiter owns the offer
        if (!offreEmploi.getRecruteur().getEmail().equals(email) || authentication.getAuthorities().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette offre.");
        }

        offreEmploiRepository.deleteById(id);
    }

    public Optional<OffreEmploi> getOffreById(Long id) {
        return offreEmploiRepository.findById(id);
    }

    public List<OffreEmploi> getAllOffresByRecruteur(long id) {
        return offreEmploiRepository.findByRecruteurId(id);
    }

    public List<OffreEmploi> getAllOffres() {
        return offreEmploiRepository.findAll();
    }
}