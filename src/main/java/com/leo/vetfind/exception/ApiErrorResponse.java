package com.leo.vetfind.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard error response")
public record ApiErrorResponse(

        @Schema(description = "Timestamp of the error", example = "2026-01-20T20:00:00")
        LocalDateTime timestamp,

        @Schema(description = "HTTP STATUS CODE", example = "400")
        int status,

        @Schema(description = "Error type", example = "Validation error")
        String error,

        @Schema(description = "Error message", example = "Email is required")
        String message,

        @Schema(description = "Request path", example = "/api/users")
        String path)
{
}