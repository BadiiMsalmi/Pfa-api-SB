package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.Models.Application;
import com.tekup.pfaapisb.Services.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @Secured("ROLE_CANDIDAT")
    @PostMapping("/apply/{candidatId}/{offreId}")
    public String apply(@PathVariable Long candidatId, @PathVariable Long offreId) {
        return applicationService.apply(candidatId, offreId);
    }


    //tjib les application eli aamalhom el candidat ID
    //tatlaa3 lel candiat
    @GetMapping("/candidat/{candidatId}")
    public List<Application> getApplicationsForCandidat(@PathVariable Long candidatId) {
        return applicationService.getApplicationsForCandidat(candidatId);
    }

    //tjib les candidat eli applikew aal offre hedh
    //tatlaa lel recruter
    @GetMapping("/offre/{offreId}")
    public List<Application> getApplicationsForOffre(@PathVariable Long offreId,Authentication authentication) {
        return applicationService.getApplicationsForOffre(offreId,authentication.getName());
    }

    @Secured("ROLE_CANDIDAT")
    @DeleteMapping("/annuler/{id}")
    public ResponseEntity<Void> annulerCandidature(
            @PathVariable Long id,
            Authentication authentication) {
        applicationService.annulerApplication(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/acceptApplication/{appId}")
    public void accepterApplication(@PathVariable Long appId,Authentication authentication) {
        applicationService.acceptApplication(appId,authentication.getName());
    }

    @PostMapping("/refuserApplication/{appId}")
    public void refuserApplication(@PathVariable Long appId,Authentication authentication) {
        applicationService.refuserApplication(appId,authentication.getName());
    }
}
