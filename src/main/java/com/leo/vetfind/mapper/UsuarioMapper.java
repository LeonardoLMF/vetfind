package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.usuario.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // dto > entity
    Usuario toEntity(CadastroUsuarioRequestDTO dto);


    //entity > dto
    CadastroUsuarioResponseDTO toResponseDTO(Usuario usuario);

    //lista
    List<CadastroUsuarioResponseDTO> toResponseDTOList (List<Usuario> usuarios);
}
