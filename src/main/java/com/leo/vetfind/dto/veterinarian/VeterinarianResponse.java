package com.leo.vetfind.dto.veterinarian;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Veterinarian profile response")
public class VeterinarianResponse {

    @Schema(description = "Veterinarian profile ID", example = "1")
    private Long id;

    @Schema(description = "CRMV registration number", example = "SP12345")
    private String crmv;

    @Schema(description = "Associated user ID", example = "2")
    private Long userId;
}
