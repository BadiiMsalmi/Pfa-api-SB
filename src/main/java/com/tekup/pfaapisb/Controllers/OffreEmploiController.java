package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.DTO.OffreEmploiDTO;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Services.OffreEmploiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offres")
@AllArgsConstructor
public class OffreEmploiController {

    private final OffreEmploiService offreEmploiService;

    @Secured("ROLE_RECRUTEUR")
    @PostMapping("/postOffre")
    public ResponseEntity<OffreEmploi> createOffre(@RequestBody OffreEmploiDTO offreEmploiDTO) {
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


    @GetMapping("trouverOffre/{id}")
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
            @RequestBody OffreEmploiDTO offreEmploiDTO,
            Authentication authentication) {
        OffreEmploi updatedOffre = offreEmploiService.updateOffre(id, offreEmploiDTO,authentication.getName());
        return ResponseEntity.ok(updatedOffre);
    }

    @Secured({"ROLE_RECRUTEUR","ROLE_ADMIN"})
    @DeleteMapping("/deleteoffre/{id}")
    public ResponseEntity<Void> deleteOffre(
            @PathVariable Long id,
            Authentication authentication
            ) {
        offreEmploiService.deleteOffreById(id,authentication.getName(),authentication);
        return ResponseEntity.noContent().build();
    }

    //sauvgarder offre
    @PostMapping("/saveOffre/{id}")
    public ResponseEntity<String> saveOffre(
            @PathVariable Long id,
            Authentication authentication) {
        offreEmploiService.saveOffre(id, authentication.getName());
        return ResponseEntity.ok("Offre saved successfully");
    }


    @DeleteMapping("/unsaveOffre/{id}")
    public ResponseEntity<String> unsaveOffre(
            @PathVariable Long id,
            Authentication authentication) {
        offreEmploiService.unsaveOffre(id, authentication.getName());
        return ResponseEntity.ok("Offre unsaved successfully");
    }




}
