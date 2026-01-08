package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.dto.usuario.UpdateUsuarioRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

public interface UsuarioService {

    CadastroUsuarioResponseDTO criarUsuario(CadastroUsuarioRequestDTO dto);

    List<CadastroUsuarioResponseDTO> listarUsuarios();

    CadastroUsuarioResponseDTO buscarUsuarioPorId(Long id);

    CadastroUsuarioResponseDTO atualizar(Long id, UpdateUsuarioRequestDTO dto);
}
