package com.leo.vetfind.service.usuario;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.entity.Usuario;
import com.leo.vetfind.entity.Veterinario;
import com.leo.vetfind.exception.*;
import com.leo.vetfind.mapper.UsuarioMapper;
import com.leo.vetfind.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void CriarUsuarioComSucesso() {

        CadastroUsuarioRequestDTO request =
                CadastroUsuarioRequestDTO.builder()
                        .email("teste@email.com")
                        .build();

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email("teste@email.com")
                .build();

        CadastroUsuarioResponseDTO response =
                CadastroUsuarioResponseDTO.builder()
                        .id(1L)
                        .email("teste@email.com")
                        .build();

        when(usuarioRepository.existsByEmail("teste@email.com"))
                .thenReturn(false);

        when(usuarioMapper.toEntity(request))
                .thenReturn(usuario);

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(response);

        CadastroUsuarioResponseDTO resultado =
                usuarioService.criarUsuario(request);

        assertNotNull(resultado);
        assertEquals("teste@email.com", resultado.getEmail());

        verify(usuarioRepository).existsByEmail("teste@email.com");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void LancarExcecaoQuandoEmailJaCadastrado() {

        CadastroUsuarioRequestDTO request =
                CadastroUsuarioRequestDTO.builder()
                        .email("teste@email.com")
                        .build();

        when(usuarioRepository.existsByEmail("teste@email.com"))
                .thenReturn(true);

        assertThrows(EmailJaCadastradoException.class,
                () -> usuarioService.criarUsuario(request));

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void BuscarUsuarioPorIdComSucesso() {

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email("teste@email.com")
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(
                        CadastroUsuarioResponseDTO.builder()
                                .id(1L)
                                .email("teste@email.com")
                                .build()
                );

        CadastroUsuarioResponseDTO resultado =
                usuarioService.buscarUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoExistir() {

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class,
                () -> usuarioService.buscarUsuarioPorId(99L));
    }

    @Test
    void DeletarUsuarioComSucesso() {

        Usuario usuario = Usuario.builder()
                .id(1L)
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void LancarExcecaoAoDeletarUsuarioComVeterinario() {

        Usuario usuario = Usuario.builder()
                .id(1L)
                .veterinario(new Veterinario())
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        assertThrows(UsuarioPossuiVeterinarioException.class,
                () -> usuarioService.deletarUsuario(1L));

        verify(usuarioRepository, never()).delete(any());
    }
}
