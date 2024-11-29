package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.DTO.OffreEmploiIDTO;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Services.OffreEmploiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
@AllArgsConstructor
public class OffreEmploiController {

    private final OffreEmploiService offreEmploiService;

    @Secured("ROLE_RECRUTEUR")
    @PostMapping("/postOffre")
    public ResponseEntity<OffreEmploi> createOffre(@RequestBody OffreEmploiIDTO offreEmploiDTO) {
        OffreEmploi createdOffre = offreEmploiService.createOffre(offreEmploiDTO);
        return ResponseEntity.status(201).body(createdOffre);
    }


    @GetMapping("/allOffresByRec/{id}")
    public ResponseEntity<List<OffreEmploi>> getAllOffresByRec(@PathVariable ("id") long id) {
        List<OffreEmploi> offres = offreEmploiService.getAllOffresByRecruteur(id);
        return ResponseEntity.ok(offres);
    }


    @GetMapping("/allOffres")
    public ResponseEntity<List<OffreEmploi>> getAllOffres() {
        List<OffreEmploi> offres = offreEmploiService.getAllOffres();
        return ResponseEntity.ok(offres);
    }


    @GetMapping("trouver/{id}")
    public ResponseEntity<OffreEmploi> getOffreById(
            @PathVariable Long id
    ) {
        OffreEmploi offreEmploi = offreEmploiService.getOffreById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        return ResponseEntity.ok(offreEmploi);
    }


    @PatchMapping("/updateoffre/{id}")
    public ResponseEntity<OffreEmploi> updateOffre(
            @PathVariable Long id,
            @RequestBody OffreEmploiIDTO offreEmploiDTO,
            Authentication authentication) {
        OffreEmploi updatedOffre = offreEmploiService.updateOffre(id, offreEmploiDTO,authentication.getName());
        return ResponseEntity.ok(updatedOffre);
    }

    @Secured({"ROLE_RECRUTEUR","ROLE_ADMIN"})
    @DeleteMapping("deleteoffre/{id}")
    public ResponseEntity<Void> deleteOffre(
            @PathVariable Long id,
            Authentication authentication
            ) {
        offreEmploiService.deleteOffreById(id,authentication.getName(),authentication);
        return ResponseEntity.noContent().build();
    }
}
