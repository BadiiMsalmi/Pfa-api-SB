package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.Models.UserEntity;
import com.tekup.pfaapisb.Services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UserEntityService userEntityService;

    @GetMapping("/allUsers")
    public List<UserEntity> getAllUsers() {
        return userEntityService.loadAllUsers();
    }
}
