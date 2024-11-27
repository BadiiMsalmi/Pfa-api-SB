package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.DTO.OffreEmploiIDTO;
import com.tekup.pfaapisb.Models.OffreEmploi;
import com.tekup.pfaapisb.Services.OffreEmploiService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @GetMapping
    public ResponseEntity<List<OffreEmploi>> getAllOffres() {
        List<OffreEmploi> offres = offreEmploiService.getAllOffres();
        return ResponseEntity.ok(offres);
    }

    @GetMapping("trouver/{id}")
    public ResponseEntity<OffreEmploi> getOffreById(@PathVariable Long id) {
        OffreEmploi offreEmploi = offreEmploiService.getOffreById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        return ResponseEntity.ok(offreEmploi);
    }

    @PutMapping("updateoffre/{id}")
    public ResponseEntity<OffreEmploi> updateOffre(@PathVariable Long id, @RequestBody OffreEmploiIDTO offreEmploiDTO) {
        OffreEmploi updatedOffre = offreEmploiService.updateOffre(id, offreEmploiDTO);
        return ResponseEntity.ok(updatedOffre);
    }

    @DeleteMapping("deleteoffre/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        offreEmploiService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }
}
