package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.usuario.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(CadastroUsuarioRequestDTO dto);
    CadastroUsuarioResponseDTO toResponseDTO(Usuario usuario);
}
