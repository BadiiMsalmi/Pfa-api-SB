package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.CompetenceDTO;
import com.tekup.pfaapisb.Models.Competence;
import com.tekup.pfaapisb.Repositories.CompetenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdministrateurService {

    private final CompetenceRepository comptenceRepository;

    public CompetenceDTO addNewCompetence(CompetenceDTO competenceDTO) {
        Competence competence = new Competence();
        competence.setName(competenceDTO.getName());
        comptenceRepository.save(competence);

        return CompetenceDTO.builder()
                .name(competence.getName())
                .build();
    }

}
