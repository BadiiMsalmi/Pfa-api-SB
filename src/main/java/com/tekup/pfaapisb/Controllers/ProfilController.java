package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.DTO.AuthenticationResponse;
import com.tekup.pfaapisb.DTO.ProfilCandidatDTO;
import com.tekup.pfaapisb.DTO.ProfilRecruteurDTO;
import com.tekup.pfaapisb.Services.CandidatService;
import com.tekup.pfaapisb.Services.RecruteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfilController {

    private final CandidatService candidatService;
    private final RecruteurService recruteurService;

    @PostMapping("/candidat/completeProfile")
    public void completeProfileCandidat(@RequestBody ProfilCandidatDTO request) {
        candidatService.completeCandidatProfile(request);
    }

    @PostMapping("/recruteur/completeProfile")
    public void completeProfileRecruteur(@RequestBody ProfilRecruteurDTO request) {
        recruteurService.completeRecruteurProfile(request);
    }
}
