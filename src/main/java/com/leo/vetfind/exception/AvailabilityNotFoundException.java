package com.leo.vetfind.exception;

public class AvailabilityNotFoundException extends BusinessException {
    public AvailabilityNotFoundException(Long id) {
        super("Availability with ID " + id + " not found");
    }
}