package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.Models.UserEntity;
import com.tekup.pfaapisb.Repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserEntity) userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
