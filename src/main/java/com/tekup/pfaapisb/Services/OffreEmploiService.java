package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.OffreEmploiIDTO;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Models.Recruteur;
import com.tekup.pfaapisb.Repositories.OffreEmploiRepository;
import com.tekup.pfaapisb.Repositories.RecruteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OffreEmploiService {


    private final OffreEmploiRepository offreEmploiRepository;
    private final RecruteurRepository recruteurRepository;

    public OffreEmploi createOffre(OffreEmploiIDTO offreEmploiDTO) {
        OffreEmploi offreEmploi = new OffreEmploi();
        Recruteur recruteur = recruteurRepository.findRecruteurById(offreEmploiDTO.getRecruteurId());
        offreEmploi.setTitre(offreEmploiDTO.getTitre());
        offreEmploi.setDescription(offreEmploiDTO.getDescription());
        offreEmploi.setCompetences(offreEmploiDTO.getCompetences());
        offreEmploi.setExperience(offreEmploiDTO.getExperience());
        offreEmploi.setLocalisation(offreEmploiDTO.getLocalisation());
        offreEmploi.setSalaire(offreEmploiDTO.getSalaire());
        offreEmploi.setRecruteur(recruteur);
        return offreEmploiRepository.save(offreEmploi);
    }
    public Optional<OffreEmploi> getOffreById(Long id) {
        return offreEmploiRepository.findById(id);
    }
    public List<OffreEmploi> getAllOffres() {
        return offreEmploiRepository.findAll();
    }
    public OffreEmploi updateOffre(Long id, OffreEmploiIDTO offreEmploiDTO) {
        OffreEmploi offreEmploi = offreEmploiRepository.findById(id).orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        offreEmploi.setTitre(offreEmploiDTO.getTitre());
        offreEmploi.setDescription(offreEmploiDTO.getDescription());
        offreEmploi.setCompetences(offreEmploiDTO.getCompetences());
        offreEmploi.setExperience(offreEmploiDTO.getExperience());
        offreEmploi.setLocalisation(offreEmploiDTO.getLocalisation());
        offreEmploi.setSalaire(offreEmploiDTO.getSalaire());
        return offreEmploiRepository.save(offreEmploi);
    }
    public void deleteOffre(Long id) {
        offreEmploiRepository.deleteById(id);
    }
}