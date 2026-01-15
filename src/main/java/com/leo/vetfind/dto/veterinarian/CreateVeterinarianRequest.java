package com.leo.vetfind.dto.veterinarian;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVeterinarianRequest {

    @NotBlank(message = "CRMV is required")
    private String crmv;

    @NotNull(message = "User ID is required")
    private Long userId;
}
