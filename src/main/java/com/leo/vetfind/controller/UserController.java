package com.leo.vetfind.controller;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.dto.user.UpdateUserRequest;
import com.leo.vetfind.service.usuario.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user (OWNER or VETERINARIAN)")
    @ApiResponse(responseCode = "200", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<UserResponse> createUser (@Valid @RequestBody CreateUserRequest dto) {
        UserResponse response = userService.createUser(dto);
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "List all users", description = "Returns a list of all users ordered by ID")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        List<UserResponse> list = userService.findAllUsers();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Get user by ID", description = "Returns a user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        UserResponse user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "Update user", description = "Updates an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }


    @Operation(summary = "Delete user", description = "Deletes a user from the system. Cannot delete users with Veterinarian profile")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "400", description = "Cannot delete user with veterinarian profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
