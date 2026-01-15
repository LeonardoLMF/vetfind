package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.dto.user.UpdateUserRequest;
import com.leo.vetfind.entity.User;
import com.leo.vetfind.exception.EmailAlreadyExistsException;
import com.leo.vetfind.exception.UserNotFoundException;
import com.leo.vetfind.exception.UserHasVeterinarianException;
import com.leo.vetfind.mapper.UserMapper;
import com.leo.vetfind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse criarUsuario(CreateUserRequest dto) {
        // Garantir que o email seja unico
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // cria um usuario e persiste o mesmo
        User usuario = userMapper.toEntity(dto);
        User salvo = userRepository.save(usuario);
        return userMapper.toResponseDTO(salvo);
    }

    // busca todos os usuarios (get all)
    @Override
    public List<UserResponse> listarUsuarios() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    // busca um usuario com ID especifico (get by id)
    @Override
    public UserResponse buscarUsuarioPorId(Long id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(usuario);
    }

    @Override
    public UserResponse atualizar(Long id, UpdateUserRequest dto) {

        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // email não pode ser duplicado
        if (!usuario.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // atualiza     apenas campos permitidos
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());

        User atualizado = userRepository.save(usuario);

        return userMapper.toResponseDTO(atualizado);
    }

    @Override
    public void deletarUsuario(Long id) {

        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (usuario.getVeterinario() != null) {
            throw new UserHasVeterinarianException();
        }

        userRepository.delete(usuario);
    }

}
