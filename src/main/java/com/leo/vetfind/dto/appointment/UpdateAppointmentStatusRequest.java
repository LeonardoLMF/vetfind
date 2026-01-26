package com.leo.vetfind.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request for updating appointment status (optional reason)")
public class UpdateAppointmentStatusRequest {

    @Schema(description = "Reason for status change (optional)", example = "Schedule conflict")
    private String reason;
}