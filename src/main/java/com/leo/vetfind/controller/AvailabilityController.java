package com.leo.vetfind.controller;

import com.leo.vetfind.dto.availability.AvailabilityResponse;
import com.leo.vetfind.dto.availability.CreateAvailabilityRequest;
import com.leo.vetfind.dto.availability.UpdateAvailabilityRequest;
import com.leo.vetfind.service.availability.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarians/{veterinarianId}/availabilities")
@RequiredArgsConstructor
@Tag(name = "Availabilities", description = "Veterinarian availability management endpoints")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    @Operation(summary = "Create availability", description = "Creates a new availability schedule for a veterinarian")
    @ApiResponse(responseCode = "200", description = "Availability created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data or time conflict")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    public ResponseEntity<AvailabilityResponse> createAvailability(
            @Parameter(description = "Veterinarian ID", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Valid @RequestBody CreateAvailabilityRequest dto) {

        AvailabilityResponse response = availabilityService.createAvailability(veterinarianId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "List veterinarian availabilities", description = "Returns all availabilities for a specific veterinarian")
    @ApiResponse(responseCode = "200", description = "Availabilities retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Veterinarian not found")
    public ResponseEntity<List<AvailabilityResponse>> findAllByVeterinarian(
            @Parameter(description = "Veterinarian ID", required = true, example = "1")
            @PathVariable Long veterinarianId) {

        List<AvailabilityResponse> availabilities = availabilityService.findAllByVeterinarian(veterinarianId);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/{availabilityId}")
    @Operation(summary = "Get availability by ID", description = "Returns a specific availability by its ID")
    @ApiResponse(responseCode = "200", description = "Availability found")
    @ApiResponse(responseCode = "404", description = "Availability not found")
    public ResponseEntity<AvailabilityResponse> findById(
            @Parameter(description = "Veterinarian ID", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Parameter(description = "Availability ID", required = true, example = "1")
            @PathVariable Long availabilityId) {

        AvailabilityResponse response = availabilityService.findById(availabilityId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{availabilityId}")
    @Operation(summary = "Update availability", description = "Updates an existing availability schedule")
    @ApiResponse(responseCode = "200", description = "Availability updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid data or time conflict")
    @ApiResponse(responseCode = "404", description = "Availability not found")
    public ResponseEntity<AvailabilityResponse> updateAvailability(
            @Parameter(description = "Veterinarian ID", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Parameter(description = "Availability ID", required = true, example = "1")
            @PathVariable Long availabilityId,
            @Valid @RequestBody UpdateAvailabilityRequest dto) {

        AvailabilityResponse response = availabilityService.updateAvailability(availabilityId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{availabilityId}")
    @Operation(summary = "Delete availability", description = "Deletes an availability schedule")
    @ApiResponse(responseCode = "204", description = "Availability deleted successfully")
    @ApiResponse(responseCode = "404", description = "Availability not found")
    public ResponseEntity<Void> deleteAvailability(
            @Parameter(description = "Veterinarian ID", required = true, example = "1")
            @PathVariable Long veterinarianId,
            @Parameter(description = "Availability ID", required = true, example = "1")
            @PathVariable Long availabilityId) {

        availabilityService.deleteAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }
}