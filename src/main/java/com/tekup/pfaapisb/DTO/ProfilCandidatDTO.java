package com.tekup.pfaapisb.DTO;


import com.tekup.pfaapisb.Models.Competence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilCandidatDTO {

    private String email;

    private List<Competence> competences;

    private int experiences;
}
