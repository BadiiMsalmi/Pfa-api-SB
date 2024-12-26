package com.tekup.pfaapisb.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tekup.pfaapisb.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    private Role role;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private UserEntity user;
}
