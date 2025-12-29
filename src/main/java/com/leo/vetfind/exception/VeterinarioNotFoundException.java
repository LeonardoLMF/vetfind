package com.leo.vetfind.exception;

public class VeterinarioNotFoundException extends BusinessException{

    public VeterinarioNotFoundException(){
        super("Veterinario não encontrado");
    }

}
