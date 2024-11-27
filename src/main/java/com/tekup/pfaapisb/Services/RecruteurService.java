package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.AuthenticationResponse;
import com.tekup.pfaapisb.DTO.ProfilRecruteurDTO;
import com.tekup.pfaapisb.Models.Recruteur;
import com.tekup.pfaapisb.Repositories.RecruteurRepository;
import com.tekup.pfaapisb.Validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruteurService {

    private final ObjectsValidator<ProfilRecruteurDTO> profilRecruteurValidator;
    private final RecruteurRepository recruteurRepository;

    public void completeRecruteurProfile(ProfilRecruteurDTO request) {
        profilRecruteurValidator.validate(request);

        Recruteur recruteur = recruteurRepository.findRecruteursByEmail(request.getEmail());
        recruteur.setEntreprise(request.getEntreprise());
        recruteur.setProfileCompleted(true);

        recruteurRepository.save(recruteur);
    }

}
