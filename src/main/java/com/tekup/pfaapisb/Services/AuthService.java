package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.AuthenticationRequest;
import com.tekup.pfaapisb.Models.*;
import com.tekup.pfaapisb.Enum.Role;
import com.tekup.pfaapisb.Repositories.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tekup.pfaapisb.DTO.AuthenticationResponse;
import com.tekup.pfaapisb.DTO.RegisterRequest;
import com.tekup.pfaapisb.Validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final ObjectsValidator<RegisterRequest> registerRequestValidator;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final UserEntityService userEntityService;

    public AuthenticationResponse register(RegisterRequest request) {
        var violations = registerRequestValidator.validate(request);
        if (!violations.isEmpty()) {
            return AuthenticationResponse.builder()
                    .error(String.join(" \n ", violations))
                    .build();
        }

        // Check email uniqueness
        if (isEmailAlreadyUsed(request.getEmail())) {
            return AuthenticationResponse.builder()
                    .error("Email already in use")
                    .build();
        }

        // Register user based on role
        String jwtToken;
        if (request.getRole() == Role.ROLE_CANDIDAT) {
            Candidat candidat = new Candidat();
            candidat.setAccountname(request.getAccountname());
            candidat.setFirstname(request.getFirstname());
            candidat.setLastname(request.getLastname());
            candidat.setEmail(request.getEmail());
            candidat.setPassword(passwordEncoder.encode(request.getPassword()));
            candidat.setRole(Role.ROLE_CANDIDAT);
            candidat.setInscriptionDate(new Date());
            Candidat savedCandidat = candidatRepository.save(candidat);
            jwtToken = jwtService.generateToken(savedCandidat);
            saveUserToken(savedCandidat, jwtToken);

        }else if(request.getRole() == Role.ROLE_RECRUTEUR){
            Recruteur recruteur = new Recruteur();
            recruteur.setAccountname(request.getAccountname());
            recruteur.setFirstname(request.getFirstname());
            recruteur.setLastname(request.getLastname());
            recruteur.setEmail(request.getEmail());
            recruteur.setInscriptionDate(new Date());
            recruteur.setRole(Role.ROLE_RECRUTEUR);
            recruteur.setPassword(passwordEncoder.encode(request.getPassword()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);
            jwtToken = jwtService.generateToken(savedRecruteur);
            saveUserToken(savedRecruteur, jwtToken);

        }else{
            return AuthenticationResponse.builder().error("Role not supported").build();
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }


    private boolean isEmailAlreadyUsed(String email) {
        return candidatRepository.findByEmail(email).isPresent()
                || recruteurRepository.findByEmail(email).isPresent()
                || administrateurRepository.findByEmail(email).isPresent();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        } catch (AuthenticationException ex) {
            return AuthenticationResponse.builder()
                    .error("Invalid email or password.")
                    .build();
        }

        UserEntity user = null;
        try {
            user = userEntityService.loadUserByUsername(request.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }


        var jwtToken = jwtService.generateToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);
        if (user.isProfileCompleted()){
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .error("Profile not completed")
                .build();
    }


    private void revokeAllUserToken(UserEntity user) {
        var validUserToken = tokenRepository.findByUserIdAndExpiredIsFalseAndRevokedIsFalse(user.getId());
        if (!validUserToken.isEmpty()) {
            validUserToken.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validUserToken);
        }
    }

}
