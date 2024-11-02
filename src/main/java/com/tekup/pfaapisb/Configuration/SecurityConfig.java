package com.tekup.pfaapisb.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public LogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless REST APIs using the updated approach
                .csrf(AbstractHttpConfigurer::disable)

                // Enable CORS for cross-origin requests, if required
                .cors(AbstractHttpConfigurer::disable)

                // Set up route authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                // Set session management to stateless for JWT-based authentication
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure custom authentication provider and add JWT filter
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // Configure logout handling
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(securityContextLogoutHandler())
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

}
