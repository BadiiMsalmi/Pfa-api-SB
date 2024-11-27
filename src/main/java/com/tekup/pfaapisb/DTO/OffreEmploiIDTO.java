package com.tekup.pfaapisb.DTO;

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
    private List<String> competences;
    private int experience;
    private String localisation;
    private double salaire;
    private Long recruteurId;


}
