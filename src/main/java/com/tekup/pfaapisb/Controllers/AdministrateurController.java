package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.DTO.CompetenceDTO;
import com.tekup.pfaapisb.Models.Competence;
import com.tekup.pfaapisb.Services.AdministrateurService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/administrateur")
@RequiredArgsConstructor
public class AdministrateurController {

    private final AdministrateurService administrateurService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/competence/addNew")
    public ResponseEntity<CompetenceDTO> addNewCompetence(@RequestBody CompetenceDTO competenceDTO){
        System.out.println(competenceDTO);
        return ResponseEntity.ok(administrateurService.addNewCompetence(competenceDTO));
    }

}
