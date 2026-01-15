package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // dto > entity
    User toEntity(CadastroUsuarioRequestDTO dto);


    //entity > dto
    CadastroUsuarioResponseDTO toResponseDTO(User usuario);

    //lista
    List<CadastroUsuarioResponseDTO> toResponseDTOList (List<User> usuarios);
}
