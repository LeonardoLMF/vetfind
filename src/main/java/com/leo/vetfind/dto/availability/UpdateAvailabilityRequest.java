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
@Schema(description = "Request for updating availability")
public class UpdateAvailabilityRequest {

    @NotNull(message = "Day of week is required")
    @Schema(description = "Day of the week", example = "TUESDAY", allowableValues = {
            "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    })
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    @Schema(description = "Start time", example = "09:00:00", type = "string", format = "time")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Schema(description = "End time", example = "17:00:00", type = "string", format = "time")
    private LocalTime endTime;

    @NotNull(message = "Active status is required")
    @Schema(description = "Whether the availability is active", example = "true")
    private Boolean active;
}