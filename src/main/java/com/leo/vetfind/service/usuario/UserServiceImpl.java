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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest dto) {
        // Garantir que o email seja unico
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // cria um usuario e persiste o mesmo
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    // busca todos os veterinarios odenados por id
    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    // busca um usuario com ID especifico (get by id)
    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // email não pode ser duplicado
        if (!user.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // atualiza     apenas campos permitidos
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());

        User updated = userRepository.save(user);

        return userMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getVeterinarian() != null) {
            throw new UserHasVeterinarianException();
        }

        userRepository.delete(user);
    }

}
