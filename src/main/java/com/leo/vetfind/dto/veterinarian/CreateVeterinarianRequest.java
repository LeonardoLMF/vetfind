package com.leo.vetfind.dto.veterinarian;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for creating a veterinarian profile")
public class CreateVeterinarianRequest {

    @NotBlank(message = "CRMV is required")
    @Schema(description = "Veterinarians CRMV number", example = "SP12345")
    private String crmv;


    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user to link (Must be VETERINARIO type)", example = "2")
    private Long userId;
}
