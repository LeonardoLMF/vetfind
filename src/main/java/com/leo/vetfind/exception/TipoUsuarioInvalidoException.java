package com.leo.vetfind.exception;

public class TipoUsuarioInvalidoException extends BusinessException{
    public TipoUsuarioInvalidoException(){
        super("User não é do tipo VETERINARIO");
    }
}
