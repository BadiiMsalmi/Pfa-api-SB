package com.tekup.pfaapisb.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekup.pfaapisb.Enum.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    @JsonIgnore
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "offre_id", nullable = false)
    @JsonIgnore
    private OffreEmploi offre;

    private LocalDateTime appliedAt;

    private ApplicationStatus status;


}
