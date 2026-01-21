// src/main/java/com/leo/vetfind/controller/AuthController.java
package com.leo.vetfind.controller;

import com.leo.vetfind.entity.User;
import com.leo.vetfind.exception.InvalidCredentialsException;
import com.leo.vetfind.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Validates user credentials (email and password)")
    @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return ResponseEntity.ok(new LoginResponse(
                "Login successful",
                user.getId(),
                user.getEmail(),
                user.getUserType().toString()
        ));
    }
}

@Schema(description = "Login credentials")
record LoginRequest(
        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        @Schema(description = "User's email", example = "user@example.com")
        String email,

        @NotBlank(message = "Password is required")
        @Schema(description = "User's password", example = "Senha@123")
        String password
) {}

@Schema(description = "Login response")
record LoginResponse(
        @Schema(description = "Success message")
        String message,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "User email")
        String email,

        @Schema(description = "User type", example = "PROPRIETARIO")
        String userType
) {}