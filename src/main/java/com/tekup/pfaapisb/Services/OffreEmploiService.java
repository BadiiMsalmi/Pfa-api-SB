package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.OffreEmploiDTO;
import com.tekup.pfaapisb.DTO.OffreEmploiSearchDTO;
import com.tekup.pfaapisb.Enum.OffreStatus;
import com.tekup.pfaapisb.Models.Candidat;
import com.tekup.pfaapisb.Models.Competence;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Models.Recruteur;
import com.tekup.pfaapisb.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OffreEmploiService {


    private final OffreEmploiRepository offreEmploiRepository;
    private final RecruteurRepository recruteurRepository;
    private final CompetenceRepository competenceRepository;
    private final CandidatRepository candidatRepository;
    private final OffreEmploiCustomRepository customRepository;

    public OffreEmploi createOffre(OffreEmploiDTO offreEmploiDTO) {
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
        offreEmploi.setStatus(OffreStatus.OPEN);
        return offreEmploiRepository.save(offreEmploi);
    }

    public OffreEmploi updateOffre(Long id, OffreEmploiDTO offreEmploiDTO, String email) {
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
        offreEmploi.setStatus(offreEmploiDTO.getStatus());
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

        if (offreEmploi.getStatus().equals(OffreStatus.OPEN)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette offre car elle est encore ouverte.");
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


    public OffreEmploi saveOffre(Long id, String email) {
        OffreEmploi offreEmploi = offreEmploiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre not found"));
        Candidat candidat = candidatRepository.findCandidatByEmail(email);

        if (!candidat.getOffreEmploiSauvgarder().contains(offreEmploi)) {
            candidat.getOffreEmploiSauvgarder().add(offreEmploi);
            candidatRepository.save(candidat);
        }
        return offreEmploi;
    }


    public void unsaveOffre(Long offreId,String email) {
        // Retrieve the candidate by their ID
        Candidat candidat = candidatRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found " ));

        // Retrieve the job offer by its ID
        OffreEmploi offre = offreEmploiRepository.findById(offreId)
                .orElseThrow(() -> new EntityNotFoundException("OffreEmploi not found with ID: " + offreId));

        // Remove the job offer from the candidate's saved offers
        if (candidat.getOffreEmploiSauvgarder().contains(offre)) {
            candidat.getOffreEmploiSauvgarder().remove(offre);
            candidatRepository.save(candidat);
        } else {
            throw new IllegalStateException("The job offer is not saved by this candidate.");
        }
    }


    /*
    public ResponseEntity<List<OffreEmploiSearchDTO>> searchOffres(
            String titre,
            String localisation,
            Integer experience,
            Double salaire
    ) {
        List<OffreEmploi> offres = customRepository.searchOffres(titre, localisation, experience, salaire);

        List<OffreEmploiSearchDTO> result = offres.stream()
                .map(offre -> new OffreEmploiSearchDTO(
                        offre.getId(),
                        offre.getTitre(),
                        offre.getDescription(),
                        offre.getExperience(),
                        offre.getLocalisation(),
                        offre.getSalaire()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
    */
}