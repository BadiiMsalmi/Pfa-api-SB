package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.Models.Formation;
import com.tekup.pfaapisb.Services.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        return new ResponseEntity<>(formationService.getAllFormations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        return formation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addFormation")
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation newFormation = formationService.createFormation(formation);
        return new ResponseEntity<>(newFormation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        try {
            formationService.deleteFormation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
