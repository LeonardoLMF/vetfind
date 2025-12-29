package com.leo.vetfind.exception;

public class TipoUsuarioInvalidoException extends BusinessException{
    public TipoUsuarioInvalidoException(){
        super("Usuario não é do tipo VETERINARIO");
    }
}
