package com.tekup.pfaapisb.DTO;

import com.tekup.pfaapisb.Enum.Role;
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
public class RegisterRequest {

    @NotNull(message = "The firstname should not be null")
    @NotEmpty(message = "The firstname should not be empty")
    private String firstname;

    @NotNull(message = "The lastname should not be null")
    @NotEmpty(message = "The lastname should not be empty")
    private String lastname;

    @NotNull(message = "The username should not be null")
    @NotEmpty(message = "The username should not be empty")
    private String accountname;

    @NotNull(message = "The email should not be null")
    @NotEmpty(message = "The email should not be empty")
    private String email;

    @NotNull(message = "The password should not be null")
    @NotEmpty(message = "The password should not be empty")
    private String password;

    @NotNull(message = "The role should not be null")
    private Role role;
}
