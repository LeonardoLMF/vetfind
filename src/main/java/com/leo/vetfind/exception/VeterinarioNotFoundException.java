package com.leo.vetfind.exception;

public class VeterinarioNotFoundException extends BusinessException{

    public VeterinarioNotFoundException(Long id){
        super("Veterinario com o ID " + id + " não encontrado");
    }

}
