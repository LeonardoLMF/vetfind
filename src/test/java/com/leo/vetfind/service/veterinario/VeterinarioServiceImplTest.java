package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioRequestDTO;
import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.dto.veterinario.UpdateVeterinarioRequestDTO;
import com.leo.vetfind.entity.TipoUsuario;
import com.leo.vetfind.entity.usuario.Usuario;
import com.leo.vetfind.entity.veterinario.Veterinario;
import com.leo.vetfind.exception.CrmvCadastradoException;
import com.leo.vetfind.exception.UsuarioNotFoundException;
import com.leo.vetfind.exception.VeterinarioNotFoundException;
import com.leo.vetfind.mapper.VeterinarioMapper;
import com.leo.vetfind.repository.UsuarioRepository;
import com.leo.vetfind.repository.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceImplTest {

    @Mock
    private VeterinarioRepository veterinarioRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VeterinarioMapper veterinarioMapper;
    @InjectMocks
    private VeterinarioServiceImpl veterinarioService;

    @Test
    void CriarVeterinarioComSucesso() {
        //giv
        CadastroVeterinarioRequestDTO request = new CadastroVeterinarioRequestDTO(
                "CRMV123",
                1L
        );

        Usuario usuario = Usuario.builder()
                .id(1L)
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        Veterinario veterinario = Veterinario.builder()
                .id(10L)
                .crmv("CRMV123")
                .usuario(usuario)
                .build();

        CadastroVeterinarioResponseDTO responseEsperado =
                new CadastroVeterinarioResponseDTO(
                        10L,
                        "CRMV123",
                        1L
                );

        when(veterinarioRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(usuarioRepository.findById(1L))
                .thenReturn(java.util.Optional.of(usuario));

        when(veterinarioRepository.save(any(Veterinario.class)))
                .thenReturn(veterinario);

        when(veterinarioMapper.toResponseDTO(veterinario))
                .thenReturn(responseEsperado);

        //wh
        CadastroVeterinarioResponseDTO resultado =
                veterinarioService.criarVeterinario(request);

        //th
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("CRMV123", resultado.getCrmv());
        assertEquals(1L, resultado.getUsuarioId());

        verify(veterinarioRepository).existsByCrmv("CRMV123");
        verify(usuarioRepository).findById(1L);
        verify(veterinarioRepository).save(any(Veterinario.class));
        verify(veterinarioMapper).toResponseDTO(veterinario);

    }

    @Test
    void LancarExcecaoQuandoCrmvJaEstiverCadastrado() {

        // arrange
        CadastroVeterinarioRequestDTO dto = CadastroVeterinarioRequestDTO.builder()
                .crmv("CRMV123")
                .usuarioId(1L)
                .build();

        when(veterinarioRepository.existsByCrmv("CRMV123"))
                .thenReturn(true);

        // act + assert
        CrmvCadastradoException exception =
                assertThrows(CrmvCadastradoException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("CRMV ja cadastrado", exception.getMessage());

        verify(veterinarioRepository, never()).save(any());
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoExistir() {

        CadastroVeterinarioRequestDTO dto = CadastroVeterinarioRequestDTO.builder()
                .crmv("CRMV123")
                .usuarioId(99L)
                .build();

        when(veterinarioRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());


        UsuarioNotFoundException exception =
                assertThrows(UsuarioNotFoundException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("Usuario com o ID 99 não encontrado", exception.getMessage());

        verify(veterinarioRepository, never()).save(any());
    }

    @Test
    void BuscarVeterinarioPorIdComSucesso() {
        Veterinario veterinario = Veterinario.builder()
                .id(10L)
                .crmv("CRMV123")
                .usuario(Usuario.builder().id(1L).build())
                .build();

        when(veterinarioRepository.findById(10L))
                .thenReturn(Optional.of(veterinario));

        when(veterinarioMapper.toResponseDTO(veterinario))
                .thenReturn(new CadastroVeterinarioResponseDTO(10L, "CRMV123", 1L));

        CadastroVeterinarioResponseDTO resultado =
                veterinarioService.getById(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("CRMV123", resultado.getCrmv());
        assertEquals(1L, resultado.getUsuarioId());
    }

    @Test
    void ListarTodosVeterinarios() {
        Veterinario vet1 = Veterinario.builder()
                .id(1L)
                .crmv("CRMV1")
                .usuario(Usuario.builder().id(10L).build())
                .build();

        Veterinario vet2 = Veterinario.builder()
                .id(2L)
                .crmv("CRMV2")
                .usuario(Usuario.builder().id(20L).build())
                .build();

        when(veterinarioRepository.findByUsuario_TipoUsuario(TipoUsuario.VETERINARIO))
                .thenReturn(List.of(vet1, vet2));

        when(veterinarioMapper.toResponseDTO(vet1))
                .thenReturn(new CadastroVeterinarioResponseDTO(1L, "CRMV1", 10L));

        when(veterinarioMapper.toResponseDTO(vet2))
                .thenReturn(new CadastroVeterinarioResponseDTO(2L, "CRMV2", 20L));

        List<CadastroVeterinarioResponseDTO> resultado = veterinarioService.getAll();

        assertEquals(2, resultado.size());
    }

    @Test
    void AtualizarVeterinarioComSucesso() {

        Long veterinarioId = 10L;

        UpdateVeterinarioRequestDTO dto =
                new UpdateVeterinarioRequestDTO("CRMV999");

        Veterinario veterinarioExistente = Veterinario.builder()
                .id(veterinarioId)
                .crmv("CRMV123")
                .build();

        Veterinario veterinarioAtualizado = Veterinario.builder()
                .id(veterinarioId)
                .crmv("CRMV999")
                .build();

        CadastroVeterinarioResponseDTO responseDTO =
                new CadastroVeterinarioResponseDTO(10L, "CRMV999", null);

        when(veterinarioRepository.findById(veterinarioId))
                .thenReturn(Optional.of(veterinarioExistente));

        when(veterinarioRepository.save(any(Veterinario.class)))
                .thenReturn(veterinarioAtualizado);

        when(veterinarioMapper.toResponseDTO(veterinarioAtualizado))
                .thenReturn(responseDTO);

        CadastroVeterinarioResponseDTO resultado =
                veterinarioService.atualizar(veterinarioId, dto);

        assertNotNull(resultado);
        assertEquals("CRMV999", resultado.getCrmv());

        verify(veterinarioRepository).findById(veterinarioId);
        verify(veterinarioRepository).save(any(Veterinario.class));
    }

    @Test
    void DeletarVeterinarioInexistente() {
        when(veterinarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(VeterinarioNotFoundException.class,
                () -> veterinarioService.deletar(99L));
    }

}


