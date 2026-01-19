package com.leo.vetfind.controller;

import com.leo.vetfind.dto.veterinarian.CreateVeterinarianRequest;
import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.dto.veterinarian.UpdateVeterinarianRequest;
import com.leo.vetfind.service.veterinario.VeterinarianServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
@Tag(name="Veterinarians", description = "Veterinarians profile management endpoints")
public class VeterinarianController {

    private final VeterinarianServiceImpl veterinarioService;

    @Operation(summary = "Create a Veterinarian profile", description = "Creates a veterinarian profile linked to a existing user with VETERINARIO type")
    @ApiResponse(responseCode = "200", description = "Veterinarian profile created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data, CRMV already exists, user not found or user is not VETERINARIO type")
    @PostMapping
    public ResponseEntity<VeterinarianResponse> create(@Valid @RequestBody CreateVeterinarianRequest dto) {
        VeterinarianResponse response = veterinarioService.createVeterinarian(dto);
            return ResponseEntity.ok(response);
    }


    @Operation(summary = "List all veterinarians", description = "Returns a list of all veterinarians ordered by ID")
    @ApiResponse(responseCode = "200", description = "Veterinarian retrieved successfully")
    @GetMapping
    public ResponseEntity<List<VeterinarianResponse>> findAllVeterinarian() {
            return ResponseEntity.ok(veterinarioService.findAllVeterinarians());
    }


    @Operation(summary = "Get veterinarian by ID", description = "Returns a veterinarian by ID")
    @ApiResponse(responseCode = "200", description = "Veterinarian found")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> findVeterinarianById(@PathVariable Long id) {
        VeterinarianResponse response = veterinarioService.findVeterinarianById(id);
            return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update veterinarian", description = "Updates a veterinarian CRMV number")
    @ApiResponse(responseCode = "200", description = "Veterinarian updated successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @ApiResponse(responseCode = "400", description = "CRMV already exists")
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> updateVeterinarian (@PathVariable Long id, @Valid @RequestBody UpdateVeterinarianRequest dto) {
        VeterinarianResponse response =
                veterinarioService.updateVeterinarian(id, dto);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete veterinarian", description = "Deletes a veterinarian but keeps the associated user account")
    @ApiResponse(responseCode = "204", description = "Veterinarian profile deleted successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinarian (@PathVariable Long id) {
        veterinarioService.deleteVeterinarian(id);
        return ResponseEntity.noContent().build();
    }

}
