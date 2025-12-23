package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.entity.veterinario.Veterinario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VeterinarioMapper {

    // entity > dto
    @Mapping(source = "usuario.id", target = "usuarioId")
    CadastroVeterinarioResponseDTO toResponseDTO(Veterinario veterinario);

    // Lista
    List<CadastroVeterinarioResponseDTO> toResponseDTOList(List<Veterinario> veterinarios);
}
