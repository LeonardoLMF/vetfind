package com.leo.vetfind.exception;

public class UserHasVeterinarianException extends BusinessException{

    public UserHasVeterinarianException(){
        super("It's not possible to delete a user who has a veterinarian profile. ");
    }

}
