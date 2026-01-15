package com.leo.vetfind.dto.user;


import com.leo.vetfind.entity.UserType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 5)
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{11}", message = "Phone number must have 11 digits") // regex pego do stackoverflow
    private String phone;

    @NotNull(message = "O tipo do usuário é obrigatório")
    private UserType userType;
}
