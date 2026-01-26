package com.leo.vetfind.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for creating an appointment")
public class CreateAppointmentRequest {

    @NotNull(message = "Veterinarian ID is required")
    @Schema(description = "Veterinarian ID", example = "1")
    private Long veterinarianId;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    @Schema(description = "Appointment date", example = "10/10/2010", type = "string", format = "date")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    @Schema(description = "Appointment time", example = "14:00:00", type = "string", format = "time")
    private LocalTime appointmentTime;

    @NotBlank(message = "Animal type is required")
    @Schema(description = "Type of animal", example = "CAAAT")
    private String animalType;

    @Schema(description = "Description of the problem/service needed", example = "Vaccination for 2 cats")
    private String description;

    @NotBlank(message = "Service address is required")
    @Schema(description = "Address where the service will be performed", example = "Avenida Qualquer, House")
    private String serviceAddress;
}