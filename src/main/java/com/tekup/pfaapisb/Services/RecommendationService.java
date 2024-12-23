package com.tekup.pfaapisb.Services;
import com.tekup.pfaapisb.DTO.FormationRecommendationDTO;
import com.tekup.pfaapisb.DTO.OffreRecommendationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RestTemplate restTemplate;

    public List<OffreRecommendationDTO> getOffreRecommendations(Long candidatId) {
        String url = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:5000/recommendations/{candidatId}")
                .buildAndExpand(candidatId)
                .toUriString();

        // Make the GET request to the Flask API
        List<Map<String, Object>> recommendations = restTemplate.getForObject(url, List.class);

        // Map the response to DTOs
        List<OffreRecommendationDTO> dtoList = new ArrayList<>();
        if (recommendations != null) {
            for (Map<String, Object> recommendation : recommendations) {
                OffreRecommendationDTO dto = new OffreRecommendationDTO();
                dto.setId(((Number) recommendation.get("id")).longValue());
                dto.setTitre((String) recommendation.get("titre"));
                dto.setDescription((String) recommendation.get("description"));
                dto.setCombinedScore(((Number) recommendation.get("combined_score")).doubleValue());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }


    public List<FormationRecommendationDTO> getFormationRecommendations(Long candidatId) {
        String url = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:5000/recommendations/formations/{candidatId}")
                .buildAndExpand(candidatId)
                .toUriString();

        FormationRecommendationDTO[] response = restTemplate.getForObject(url, FormationRecommendationDTO[].class);

        return response != null ? Arrays.asList(response) : List.of();
    }
}


















