package com.tekup.pfaapisb.DTO;

import com.tekup.pfaapisb.Models.Competence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreEmploiIDTO {

    private String titre;
    private String description;
    private List<Long> competences;
    private int experience;
    private String localisation;
    private double salaire;
    private Long recruteurId;


}
