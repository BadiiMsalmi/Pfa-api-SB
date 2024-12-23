package com.tekup.pfaapisb.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String plateforme;

    @ManyToMany
    @JoinTable(
            name = "formation_competence",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;

}
