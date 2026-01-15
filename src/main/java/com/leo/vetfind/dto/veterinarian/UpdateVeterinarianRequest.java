package com.leo.vetfind.dto.veterinarian;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVeterinarianRequest {

    @NotBlank(message = "CRMV is required")
    private String crmv;

}
