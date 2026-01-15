package com.leo.vetfind.exception;

public class UserNotFoundException extends BusinessException{
    public UserNotFoundException(Long id){
        super("User with ID " + id + " not found");
    }
}
