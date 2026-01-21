package com.leo.vetfind.exception;

public class InvalidCredentialsException extends BusinessException{
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
