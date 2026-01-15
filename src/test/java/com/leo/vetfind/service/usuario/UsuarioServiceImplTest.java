package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.entity.User;
import com.leo.vetfind.entity.Veterinarian;
import com.leo.vetfind.exception.*;
import com.leo.vetfind.mapper.UserMapper;
import com.leo.vetfind.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void CriarUsuarioComSucesso() {

        CreateUserRequest request =
                CreateUserRequest.builder()
                        .email("teste@email.com")
                        .build();

        User usuario = User.builder()
                .id(1L)
                .email("teste@email.com")
                .build();

        UserResponse response =
                UserResponse.builder()
                        .id(1L)
                        .email("teste@email.com")
                        .build();

        when(userRepository.existsByEmail("teste@email.com"))
                .thenReturn(false);

        when(userMapper.toEntity(request))
                .thenReturn(usuario);

        when(userRepository.save(usuario))
                .thenReturn(usuario);

        when(userMapper.toResponseDTO(usuario))
                .thenReturn(response);

        UserResponse resultado =
                usuarioService.criarUsuario(request);

        assertNotNull(resultado);
        assertEquals("teste@email.com", resultado.getEmail());

        verify(userRepository).existsByEmail("teste@email.com");
        verify(userRepository).save(usuario);
    }

    @Test
    void LancarExcecaoQuandoEmailJaCadastrado() {

        CreateUserRequest request =
                CreateUserRequest.builder()
                        .email("teste@email.com")
                        .build();

        when(userRepository.existsByEmail("teste@email.com"))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> usuarioService.criarUsuario(request));

        verify(userRepository, never()).save(any());
    }

    @Test
    void BuscarUsuarioPorIdComSucesso() {

        User usuario = User.builder()
                .id(1L)
                .email("teste@email.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(userMapper.toResponseDTO(usuario))
                .thenReturn(
                        UserResponse.builder()
                                .id(1L)
                                .email("teste@email.com")
                                .build()
                );

        UserResponse resultado =
                usuarioService.buscarUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoExistir() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> usuarioService.buscarUsuarioPorId(99L));
    }

    @Test
    void DeletarUsuarioComSucesso() {

        User usuario = User.builder()
                .id(1L)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        verify(userRepository).delete(usuario);
    }

    @Test
    void LancarExcecaoAoDeletarUsuarioComVeterinario() {

        User usuario = User.builder()
                .id(1L)
                .veterinario(new Veterinarian())
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        assertThrows(UserHasVeterinarianException.class,
                () -> usuarioService.deletarUsuario(1L));

        verify(userRepository, never()).delete(any());
    }
}
