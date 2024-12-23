package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.DTO.FormationRecommendationDTO;
import com.tekup.pfaapisb.DTO.OffreRecommendationDTO;
import com.tekup.pfaapisb.Services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/offres/{candidatId}")
    public List<OffreRecommendationDTO> getOffresRecommendations(@PathVariable Long candidatId) {
        return recommendationService.getOffreRecommendations(candidatId);
    }

    @GetMapping("/api/v1/recommendations/formations/{candidatId}")
    public List<FormationRecommendationDTO> getFormationRecommendations(@PathVariable Long candidatId) {
        return recommendationService.getFormationRecommendations(candidatId);
    }
}
