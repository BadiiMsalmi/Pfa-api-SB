package com.tekup.pfaapisb.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidat extends UserEntity {

    private String cv;

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Application> applications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Notification> notifications;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "candidat_competences",
            joinColumns = @JoinColumn(name = "candidat_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;

    private int experiences;

    @ManyToMany
    @JoinTable(
            name = "candidat_saved_offres",
            joinColumns = @JoinColumn(name = "candidat_id"),
            inverseJoinColumns = @JoinColumn(name = "offre_id")
    )
    @JsonManagedReference
    private List<OffreEmploi> offreEmploiSauvgarder;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CANDIDAT"));
    }
}
