package com.leo.vetfind.dto.veterinarian;

import com.leo.vetfind.dto.shared.AddressDTO;
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

    @Schema(description = "User's email", example = "vet@email.com")
    private String email;

    @Schema(description = "User's phone", example = "11999999999")
    private String phone;

    @Schema(description = "User's address")
    private AddressDTO address;

    @Schema(description = "Latitude", example = "-10.5555")
    private Double latitude;

    @Schema(description = "Longitude", example = "-46.6333")
    private Double longitude;
}
