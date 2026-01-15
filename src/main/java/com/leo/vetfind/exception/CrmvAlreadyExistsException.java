package com.leo.vetfind.exception;

public class CrmvAlreadyExistsException extends BusinessException{
    public CrmvAlreadyExistsException(){
        super("CRMV already exists");
    }
}
