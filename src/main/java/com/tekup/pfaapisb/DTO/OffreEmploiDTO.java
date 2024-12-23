package com.tekup.pfaapisb.DTO;

import com.tekup.pfaapisb.Enum.OffreStatus;
import com.tekup.pfaapisb.Models.Competence;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreEmploiDTO {

    private String titre;
    private String description;
    private List<Long> competences;
    private int experience;
    private String localisation;
    private double salaire;
    private Long recruteurId;
    private OffreStatus status;


}
