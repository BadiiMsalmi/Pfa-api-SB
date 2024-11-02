package com.tekup.pfaapisb.Controllers;

import com.tekup.pfaapisb.DTO.AuthenticationResponse;
import com.tekup.pfaapisb.DTO.RegisterRequest;
import com.tekup.pfaapisb.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        System.out.println("**************************************");
        System.out.println(request);
        System.out.println("**************************************");
        return ResponseEntity.ok(authService.register(request));
    }


    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        return ResponseEntity.ok("okay oubama");
    }

}
