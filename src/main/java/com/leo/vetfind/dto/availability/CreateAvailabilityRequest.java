package com.leo.vetfind.dto.availability;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for creating availability")
public class CreateAvailabilityRequest {

    @NotNull(message = "Day of week is required")
    @Schema(description = "Day of the week", example = "MONDAY", allowableValues = {
            "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    })
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    @Schema(description = "Start time", example = "08:00:00", type = "string", format = "time")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Schema(description = "End time", example = "18:00:00", type = "string", format = "time")
    private LocalTime endTime;
}