package com.leo.vetfind.exception;

public class UsuarioPossuiVeterinarioException extends BusinessException{

    public UsuarioPossuiVeterinarioException(){
        super("Não é possivel excluir um User que possui cadastro de veterinario. ");
    }

}
