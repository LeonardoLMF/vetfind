package com.leo.vetfind.dto.user;

import com.leo.vetfind.dto.shared.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User response")
public class UserResponse {
    //não passar a senha pois é sensivel

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User's email", example = "leo.teste@gmai.com")
    private String email;

    @Schema(description = "User's phone", example = "19987654321")
    private String phone;

    @Schema(description = "Type of user", example = "OWNER OR VETERINARIAN")
    private String userType;

    @Schema(description = "User's address")
    private AddressDTO address;

    @Schema(description = "Latitude", example = "-13.2704")
    private Double latitude;

    @Schema(description = "Longitude", example = "-13.2704")
    private Double longitude;
}
