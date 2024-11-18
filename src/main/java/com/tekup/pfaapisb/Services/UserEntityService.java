package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.UserDetailsDTO;
import com.tekup.pfaapisb.Models.UserEntity;
import com.tekup.pfaapisb.Repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEntityService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    //GET USER BY EMAIL
    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserEntity) userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    //GET ALL USERES
    public List<UserDetailsDTO> loadAllUsers() {
        return userEntityRepository.findAll().stream()
                .map(user -> UserDetailsDTO.builder()
                        .id(user.getId())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .accountname(user.getAccountname())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }


    //GET USER BY ID
    public UserDetailsDTO getUserById(int id) {
        Optional<UserEntity> userOptional = userEntityRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        // Get the UserEntity
        UserEntity user = userOptional.get();

        // Map the UserEntity to UserDetailsDTO
        return UserDetailsDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .accountname(user.getAccountname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    //DELETE USER BY ID
    public void deleteUserById(int id) {
        userEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userEntityRepository.deleteById(id);
    }

    public UserDetailsDTO updateUser(int id, UserDetailsDTO userDetailsDTO) {
        Optional<UserEntity> userOptional = userEntityRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        // Get the UserEntity
        UserEntity user = userOptional.get();

        user.setFirstname(userDetailsDTO.getFirstname());
        user.setLastname(userDetailsDTO.getLastname());
        user.setAccountname(userDetailsDTO.getAccountname());
        user.setEmail(userDetailsDTO.getEmail());
        user.setRole(userDetailsDTO.getRole());

        userEntityRepository.save(user);

        return UserDetailsDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .accountname(user.getAccountname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


}
