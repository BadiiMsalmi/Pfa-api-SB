package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.DTO.OffreEmploiSearchDTO;
import com.tekup.pfaapisb.Models.Candidat;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Repositories.OffreEmploiCustomRepository;
import com.tekup.pfaapisb.Services.CandidatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/candidats")
@RequiredArgsConstructor
public class CandidatController {

    private final CandidatService candidatService;

    private final OffreEmploiCustomRepository customRepository;

    // Create new Candidat
    @PostMapping("/create")
    public ResponseEntity<Candidat> createCandidat(@RequestBody Candidat candidat) {
        Candidat createdCandidat = candidatService.saveCandidat(candidat);
        return ResponseEntity.ok(createdCandidat);
    }

    // Get all Candidats
    @GetMapping("/all")
    public ResponseEntity<List<Candidat>> getAllCandidats() {
        List<Candidat> candidats = candidatService.getAllCandidats();
        return ResponseEntity.ok(candidats);
    }

    // Get Candidat by ID
    @GetMapping("/getOne/{id}")
    public ResponseEntity<Candidat> getCandidatById(@PathVariable int id) {
        Optional<Candidat> candidat = candidatService.getCandidatById(id);

        // if found and updated return reponse entity ok
        // if not found return response entity not found
        return candidat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Candidat
    @PutMapping("/update/{id}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable int id, @RequestBody Candidat updatedCandidat) {
        Optional<Candidat> candidat = candidatService.updateCandidat(id, updatedCandidat);

        // if found and updated return reponse entity ok
        // if not found return response entity not found
        return candidat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Candidat
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable int id) {
        boolean isDeleted = candidatService.deleteCandidat(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/savedJobs")
    public ResponseEntity<List<OffreEmploi>> getSavedJobs(
            Authentication authentication) {
        List<OffreEmploi> savedOffers = candidatService.getSavedOffres(authentication.getName());
        return ResponseEntity.ok(savedOffers);
    }

    @PostMapping("/searchOffre")
    public ResponseEntity<List<OffreEmploiSearchDTO>> searchOffres(@RequestBody OffreEmploiSearchDTO request) {
        List<OffreEmploi> offres = customRepository.searchOffres(
                request.getTitre(),
                request.getLocalisation(),
                request.getExperience(),
                request.getSalaire()
        );

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


}
