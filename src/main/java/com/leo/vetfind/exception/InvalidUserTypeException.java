package com.leo.vetfind.exception;

public class InvalidUserTypeException extends BusinessException{
    public InvalidUserTypeException(){
        super("User isn't a Veterinarian");
    }
}
