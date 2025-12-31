package com.leo.vetfind.exception;

public class UsuarioNotFoundException extends BusinessException{
    public UsuarioNotFoundException(Long id){
        super("Usuario com o ID " + id + " não encontrado");
    }
}
