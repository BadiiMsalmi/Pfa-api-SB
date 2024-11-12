package com.tekup.pfaapisb.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {


    @Email(message = "Email is not well formatted")
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Password should not be null")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
