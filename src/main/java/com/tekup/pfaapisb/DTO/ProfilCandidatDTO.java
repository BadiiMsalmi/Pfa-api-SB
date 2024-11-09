package com.tekup.pfaapisb.DTO;


import com.tekup.pfaapisb.Models.Experience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilCandidatDTO {

    private String email;

    private String cv; // need to be pdf

    private List<String> competences;

    private List<Experience> experiences;
}
