package com.leo.vetfind.dto.veterinarian;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for updating veterinarian CRMV")
public class UpdateVeterinarianRequest {

    @NotBlank(message = "CRMV is required")
    @Schema(description = "", example = "New CRMV registratiobn number")
    private String crmv;

}
