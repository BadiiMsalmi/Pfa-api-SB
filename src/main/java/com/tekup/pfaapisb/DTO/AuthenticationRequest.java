package com.tekup.pfaapisb.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Password should not be null")
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
