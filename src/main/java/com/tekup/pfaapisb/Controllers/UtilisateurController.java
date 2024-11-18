package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.DTO.UserDetailsDTO;
import org.springframework.security.access.annotation.Secured;
import com.tekup.pfaapisb.Services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UserEntityService userEntityService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/allUsers")
    public List<UserDetailsDTO> getAllUsers() {
        return userEntityService.loadAllUsers();
    }

    @GetMapping("/user/{id}")
    public UserDetailsDTO getUserById(@PathVariable int id) {
        return userEntityService.getUserById(id);
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable int id) {
        this.userEntityService.deleteUserById(id);
    }




}
