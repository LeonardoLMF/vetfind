package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.dto.user.UpdateUserRequest;
import com.leo.vetfind.entity.User;
import com.leo.vetfind.exception.EmailJaCadastradoException;
import com.leo.vetfind.exception.UsuarioNotFoundException;
import com.leo.vetfind.exception.UsuarioPossuiVeterinarioException;
import com.leo.vetfind.mapper.UsuarioMapper;
import com.leo.vetfind.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UserResponse criarUsuario(CreateUserRequest dto) {
        // Garantir que o email seja unico
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        // cria um usuario e persiste o mesmo
        User usuario = usuarioMapper.toEntity(dto);
        User salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(salvo);
    }

    // busca todos os usuarios (get all)
    @Override
    public List<UserResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    // busca um usuario com ID especifico (get by id)
    @Override
    public UserResponse buscarUsuarioPorId(Long id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    public UserResponse atualizar(Long id, UpdateUserRequest dto) {

        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        // email não pode ser duplicado
        if (!usuario.getEmail().equals(dto.getEmail())
                && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        // atualiza     apenas campos permitidos
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());

        User atualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(atualizado);
    }

    @Override
    public void deletarUsuario(Long id) {

        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (usuario.getVeterinario() != null) {
            throw new UsuarioPossuiVeterinarioException();
        }

        usuarioRepository.delete(usuario);
    }

}
