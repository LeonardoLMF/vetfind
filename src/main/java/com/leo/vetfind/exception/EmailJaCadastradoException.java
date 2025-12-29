package com.leo.vetfind.exception;

public class EmailJaCadastradoException extends BusinessException{
    public EmailJaCadastradoException(){
        super("Email ja foi cadastrado");
    }
}
