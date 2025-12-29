package com.leo.vetfind.exception;

public class VeterinarioJaVinculadoException extends BusinessException{

    public VeterinarioJaVinculadoException(){
        super("Usuário ja possui cadastro de veterinario");
    }
}
