package com.tekup.pfaapisb.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekup.pfaapisb.DTO.ProfilCandidatDTO;
import com.tekup.pfaapisb.DTO.ProfilRecruteurDTO;
import com.tekup.pfaapisb.Services.CandidatService;
import com.tekup.pfaapisb.Services.RecruteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfilController {

    private final CandidatService candidatService;
    private final RecruteurService recruteurService;

    @PostMapping(value = "/candidat/completeProfile")
    public void completeProfileCandidat(
            @RequestBody String dataJson
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProfilCandidatDTO request = objectMapper.readValue(dataJson, ProfilCandidatDTO.class);
        candidatService.completeCandidatProfile(request);
    }


    @PostMapping("/recruteur/completeProfile")
    public void completeProfileRecruteur(@RequestBody ProfilRecruteurDTO request) {
        recruteurService.completeRecruteurProfile(request);
    }
}
