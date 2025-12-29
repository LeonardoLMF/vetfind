package com.leo.vetfind.service.usuario;


import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.usuario.Usuario;
import com.leo.vetfind.exception.UsuarioNotFoundException;
import com.leo.vetfind.mapper.UsuarioMapper;
import com.leo.vetfind.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public CadastroUsuarioResponseDTO criarUsuario(CadastroUsuarioRequestDTO dto) {
        // Garantir que o email seja unico
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new UsuarioNotFoundException();
        }

        // cria um usuario
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .senha(dto.getSenha()) // futuramente será criptografada
                .telefone(dto.getTelefone())
                .tipoUsuario(dto.getTipoUsuario())
                .build();

        // persiste
        Usuario salvar = usuarioRepository.save(usuario);

        // retorna o dto resposta
        return CadastroUsuarioResponseDTO.builder()
                .id(salvar.getId())
                .email(salvar.getEmail())
                .telefone(salvar.getTelefone())
                .tipoUsuario(salvar.getTipoUsuario().name())
                .build();
    }

    // busca todos os usuarios (get all)
    public List<CadastroUsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }


    // bsca um usuario com ID especifico (get by id)
    public CadastroUsuarioResponseDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }
}
