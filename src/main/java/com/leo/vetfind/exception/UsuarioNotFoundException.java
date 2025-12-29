package com.leo.vetfind.exception;

public class UsuarioNotFoundException extends BusinessException{
    public UsuarioNotFoundException(){
        super("Usuario não encontrado");
    }
}
