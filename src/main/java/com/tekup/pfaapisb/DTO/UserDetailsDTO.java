package com.tekup.pfaapisb.DTO;

import com.tekup.pfaapisb.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String accountname;

    private String email;

    private Role role;

}
