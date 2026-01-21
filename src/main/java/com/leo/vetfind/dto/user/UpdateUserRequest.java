package com.leo.vetfind.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for updating an existing user")
public class UpdateUserRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Schema(description = "User's email address", example = "leo.updatedd@gmail.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{11}", message = "Phone number must have 11 digits")
    @Schema(description = "User's phone number (11 digits)", example = "19981234888")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "The password must have at least 5 characters")
    @Schema(description = "User's new password", example = "newpassword", minLength = 5)
    private String password;

}
