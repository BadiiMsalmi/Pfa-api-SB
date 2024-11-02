package com.tekup.pfaapisb.Configuration;

import java.io.IOException;

import com.tekup.pfaapisb.Models.UserEntity;
import com.tekup.pfaapisb.Services.UserEntityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tekup.pfaapisb.Repositories.TokenRepository;
import com.tekup.pfaapisb.Services.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Ensure your JwtService has methods like extractUsername and isTokenValid
    private final UserDetailsService userDetailsService; // Custom user details service if used
    private final TokenRepository tokenRepository; // Ensure this repository exists
    private final UserEntityService userEntityService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check for the presence of the Authorization header and validate it
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continue filter chain if no valid token
            return;
        }
        jwt = authHeader.substring(7); // Extract JWT from the header
        userEmail = jwtService.extractUsername(jwt); // Extract user email from the token

        // If email is found and there's no existing authentication
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userDetails = this.userEntityService.loadUserByUsername(userEmail); // Load user

            // Validate token's existence and status in the repository
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // If token is valid, set the authentication in the security context
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Set the authentication in the context
            }
        }

        filterChain.doFilter(request, response); // Continue filter chain
    }

}
