package com.leo.vetfind.dto.availability;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Availability response")
public class AvailabilityResponse {

    @Schema(description = "Availability ID", example = "1")
    private Long id;

    @Schema(description = "Veterinarian ID", example = "2")
    private Long veterinarianId;

    @Schema(description = "Day of the week", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Schema(description = "Start time", example = "08:00:00")
    private LocalTime startTime;

    @Schema(description = "End time", example = "18:00:00")
    private LocalTime endTime;

    @Schema(description = "Whether the availability is active", example = "true")
    private Boolean active;
}