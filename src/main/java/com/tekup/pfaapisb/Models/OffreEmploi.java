package com.tekup.pfaapisb.Models;

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

    @ElementCollection
    private List<String> competences;

    private int experience;
    private String localisation;
    private double salaire;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    @ManyToMany(mappedBy = "offres")
    private List<Candidat> candidats;

}
