package com.leo.vetfind.exception;

public class EmailAlreadyExistsException extends BusinessException{
    public EmailAlreadyExistsException(){
        super("Email already exists");
    }
}
