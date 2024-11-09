package com.tekup.pfaapisb.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilRecruteurDTO {

    private String email;

    private String entreprise;
}
