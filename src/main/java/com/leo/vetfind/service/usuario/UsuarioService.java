package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.dto.user.UpdateUserRequest;

import java.util.List;

public interface UsuarioService {

    UserResponse criarUsuario(CreateUserRequest dto);

    List<UserResponse> listarUsuarios();

    UserResponse buscarUsuarioPorId(Long id);

    UserResponse atualizar(Long id, UpdateUserRequest dto);

    void deletarUsuario(Long id);
}
