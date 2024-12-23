package com.tekup.pfaapisb.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tekup.pfaapisb.Enum.OffreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreEmploi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private int experience;
    private String localisation;
    private double salaire;
    private OffreStatus status;

    @ManyToMany
    @JoinTable(
            name = "offreEmploi_competence",
            joinColumns = @JoinColumn(name = "offre_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    @JsonBackReference
    private Recruteur recruteur;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL)
    private List<Application> applications;
}
