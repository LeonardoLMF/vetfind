package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.usuario.Usuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-22T21:28:53-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(CadastroUsuarioRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.email( dto.getEmail() );
        usuario.senha( dto.getSenha() );
        usuario.telefone( dto.getTelefone() );
        usuario.tipoUsuario( dto.getTipoUsuario() );

        return usuario.build();
    }

    @Override
    public CadastroUsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        CadastroUsuarioResponseDTO.CadastroUsuarioResponseDTOBuilder cadastroUsuarioResponseDTO = CadastroUsuarioResponseDTO.builder();

        cadastroUsuarioResponseDTO.id( usuario.getId() );
        cadastroUsuarioResponseDTO.email( usuario.getEmail() );
        cadastroUsuarioResponseDTO.telefone( usuario.getTelefone() );
        if ( usuario.getTipoUsuario() != null ) {
            cadastroUsuarioResponseDTO.tipoUsuario( usuario.getTipoUsuario().name() );
        }

        return cadastroUsuarioResponseDTO.build();
    }
}
