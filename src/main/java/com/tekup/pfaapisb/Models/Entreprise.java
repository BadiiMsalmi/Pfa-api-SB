package com.tekup.pfaapisb.Models;

import com.tekup.pfaapisb.Models.Utilisateur;
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
public class Entreprise extends Utilisateur {

    private String entreprise;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<OffreEmploi> offres;
}
