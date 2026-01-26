package com.leo.vetfind.dto.appointment;

import com.leo.vetfind.entity.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Appointment response")
public class AppointmentResponse {

    @Schema(description = "Appointment ID", example = "1")
    private Long id;

    @Schema(description = "Owner (pet owner) ID", example = "1")
    private Long ownerId;

    @Schema(description = "Owner email", example = "owner@test.com")
    private String ownerEmail;

    @Schema(description = "Owner phone", example = "19987654321")
    private String ownerPhone;

    @Schema(description = "Veterinarian ID", example = "2")
    private Long veterinarianId;

    @Schema(description = "Veterinarian email", example = "vet@test.com")
    private String veterinarianEmail;

    @Schema(description = "Veterinarian phone", example = "19988887777")
    private String veterinarianPhone;

    @Schema(description = "Veterinarian CRMV", example = "SP12345")
    private String veterinarianCrmv;

    @Schema(description = "Appointment date", example = "2026-02-15")
    private LocalDate appointmentDate;

    @Schema(description = "Appointment time", example = "14:00:00")
    private LocalTime appointmentTime;

    @Schema(description = "Appointment status", example = "PENDING")
    private AppointmentStatus status;

    @Schema(description = "Animal type", example = "cat")
    private String animalType;

    @Schema(description = "Description", example = "Vaccination for 10 dogs")
    private String description;

    @Schema(description = "Service address", example = "Street x, House")
    private String serviceAddress;

    @Schema(description = "Created at")
    private LocalDateTime createdAt;

    @Schema(description = "Updated at")
    private LocalDateTime updatedAt;
}