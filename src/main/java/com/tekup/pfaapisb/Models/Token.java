package com.tekup.pfaapisb.Models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tekup.pfaapisb.Enum.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private UserEntity user;
}
