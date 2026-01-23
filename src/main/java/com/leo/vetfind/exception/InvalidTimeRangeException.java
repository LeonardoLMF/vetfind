package com.leo.vetfind.exception;

public class InvalidTimeRangeException extends BusinessException {
    public InvalidTimeRangeException() {
        super("Start time must be before end time");
    }
}