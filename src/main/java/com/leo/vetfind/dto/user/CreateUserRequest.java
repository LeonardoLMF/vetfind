package com.leo.vetfind.dto.user;


import com.leo.vetfind.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for creating a new user")
public class CreateUserRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is Required")
    @Schema(description = "User's email address", example = "leo12415@gmail.com")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 5, message = "Password must be at least 5 characters")
    @Schema(description = "User's password", example = "leo132566", minLength = 5)
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{11}", message = "Phone number must have 11 digits") // regex pego do stackoverflow
    @Schema(description = "User's phone number (11 digits)", example = "19987654321")
    private String phone;

    @NotNull(message = "O tipo do usuário é obrigatório")
    @Schema(description = "Type of user", example = "PROPRIETARIO", allowableValues = {"PROPRIETARIO", "VETERINARIO"})
    private UserType userType;
}
