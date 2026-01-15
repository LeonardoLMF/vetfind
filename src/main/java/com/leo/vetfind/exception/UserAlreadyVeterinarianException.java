package com.leo.vetfind.exception;

public class UserAlreadyVeterinarianException extends BusinessException{

    public UserAlreadyVeterinarianException(){
        super("The user is already a veterinarian ");
    }
}
