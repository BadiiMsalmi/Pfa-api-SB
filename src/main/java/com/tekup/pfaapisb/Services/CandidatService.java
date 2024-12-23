package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.ProfilCandidatDTO;
import com.tekup.pfaapisb.Models.Candidat;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Repositories.CandidatRepository;
import com.tekup.pfaapisb.Validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidatService {

    private final CandidatRepository candidatRepository;
    private final ObjectsValidator<ProfilCandidatDTO> profilCandidatValidator;

    // Save a new Candidat
    public Candidat saveCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    // Get all Candidats
    public List<Candidat> getAllCandidats() {
        return candidatRepository.findAll();
    }

    // Get a Candidat by ID
    public Optional<Candidat> getCandidatById(int id) {
        return candidatRepository.findById(id);
    }

    // Update an existing Candidat
    public Optional<Candidat> updateCandidat(int id, Candidat updatedCandidat) {
        Optional<Candidat> existingCandidat = candidatRepository.findById(id);
        if (existingCandidat.isPresent()) {
            Candidat candidat = existingCandidat.get();
            candidat.setAccountname(updatedCandidat.getAccountname());
            candidat.setFirstname(updatedCandidat.getFirstname());
            candidat.setLastname(updatedCandidat.getLastname());
            candidat.setEmail(updatedCandidat.getEmail());
            candidat.setPassword(updatedCandidat.getPassword());
            candidat.setCv(updatedCandidat.getCv());
            candidat.setCompetences(updatedCandidat.getCompetences());
            candidat.setExperiences(updatedCandidat.getExperiences());
            return Optional.of(candidatRepository.save(candidat));
        } else {
            return Optional.empty();
        }
    }

    // Delete a Candidat
    public boolean deleteCandidat(int id) {
        if (candidatRepository.existsById(id)) {
            candidatRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public void completeCandidatProfile(ProfilCandidatDTO request) {
        profilCandidatValidator.validate(request);

        Candidat candidat = candidatRepository.findCandidatByEmail(request.getEmail());
        if (candidat == null) {
            throw new EntityNotFoundException("Candidat not found with email: " + request.getEmail());
        }


        candidat.setCompetences(request.getCompetences());
        candidat.setExperiences(request.getExperiences());
        candidat.setProfileCompleted(true);

        candidatRepository.save(candidat);
    }


    public List<OffreEmploi> getSavedOffres(String email) {
        Candidat candidat = candidatRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));

        List<OffreEmploi> savedOffres = candidat.getOffreEmploiSauvgarder();
        if (savedOffres.isEmpty()) {
            throw new EntityNotFoundException("No saved job offers found for this candidat");
        }

        return savedOffres;
    }

}
