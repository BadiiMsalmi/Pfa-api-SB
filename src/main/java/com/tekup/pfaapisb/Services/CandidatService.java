package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.Models.Candidat;
import com.tekup.pfaapisb.Repositories.CandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidatService {

    private final CandidatRepository candidatRepository;

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
}
