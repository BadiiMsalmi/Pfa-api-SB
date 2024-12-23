package com.tekup.pfaapisb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreEmploiSearchDTO {
    private Long id;
    private String titre;
    private String description;
    private int experience;
    private String localisation;
    private double salaire;
}
