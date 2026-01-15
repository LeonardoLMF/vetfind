package com.leo.vetfind.exception;

public class VeterinarianNotFoundException extends BusinessException{

    public VeterinarianNotFoundException(Long id){
        super("Veterinarian with ID " + id + " not found");
    }

}
