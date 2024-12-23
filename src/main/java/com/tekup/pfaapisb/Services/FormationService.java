package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.Models.Formation;
import com.tekup.pfaapisb.Models.Competence;
import com.tekup.pfaapisb.Repositories.FormationRepository;
import com.tekup.pfaapisb.Repositories.CompetenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormationService {

    private final FormationRepository formationRepository;


    private final CompetenceRepository competenceRepository;

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(Math.toIntExact(id));
    }

    public Formation createFormation(Formation formation) {
        if (formation.getCompetences() != null) {
            List<Competence> competences = competenceRepository.findAllById(
                    formation.getCompetences().stream().map(Competence::getId).toList()
            );
            formation.setCompetences(competences);
        }
        return formationRepository.save(formation);
    }


    public void deleteFormation(Long id) {
        if (formationRepository.existsById(Math.toIntExact(id))) {
            formationRepository.deleteById(Math.toIntExact(id));
        } else {
            throw new RuntimeException("Formation not found with id " + id);
        }
    }
}
