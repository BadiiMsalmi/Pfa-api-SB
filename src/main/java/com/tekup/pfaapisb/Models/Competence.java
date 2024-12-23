package com.tekup.pfaapisb.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "competences")
    @JsonIgnore
    private List<OffreEmploi> offres;

    @ManyToMany(mappedBy = "competences")
    @JsonIgnore
    private List<Candidat> candidats;

    @ManyToMany(mappedBy = "competences")
    @JsonIgnore
    private List<Formation> formations;

}
