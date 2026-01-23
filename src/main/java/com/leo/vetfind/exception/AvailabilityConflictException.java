package com.leo.vetfind.exception;

public class AvailabilityConflictException extends BusinessException {
    public AvailabilityConflictException() {
        super("Availability conflicts with existing schedule");
    }
}