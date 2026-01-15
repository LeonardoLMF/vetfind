package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinarian.CreateVeterinarianRequest;
import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.dto.veterinarian.UpdateVeterinarianRequest;
import com.leo.vetfind.entity.User;
import com.leo.vetfind.entity.UserType;
import com.leo.vetfind.entity.Veterinarian;
import com.leo.vetfind.exception.*;
import com.leo.vetfind.mapper.VeterinarianMapper;
import com.leo.vetfind.repository.UserRepository;
import com.leo.vetfind.repository.VeterinarianRepository;
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
    private VeterinarianRepository veterinarianRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VeterinarianMapper veterinarianMapper;
    @InjectMocks
    private VeterinarioServiceImpl veterinarioService;

    @Test
    void CriarVeterinarioComSucesso() {
        //giv
        CreateVeterinarianRequest request = new CreateVeterinarianRequest(
                "CRMV123",
                1L
        );

        User usuario = User.builder()
                .id(1L)
                .userType(UserType.VETERINARIO)
                .build();

        Veterinarian veterinario = Veterinarian.builder()
                .id(10L)
                .crmv("CRMV123")
                .usuario(usuario)
                .build();

        VeterinarianResponse responseEsperado =
                new VeterinarianResponse(
                        10L,
                        "CRMV123",
                        1L
                );

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(java.util.Optional.of(usuario));

        when(veterinarianRepository.save(any(Veterinarian.class)))
                .thenReturn(veterinario);

        when(veterinarianMapper.toResponseDTO(veterinario))
                .thenReturn(responseEsperado);

        //wh
        VeterinarianResponse resultado =
                veterinarioService.criarVeterinario(request);

        //th
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("CRMV123", resultado.getCrmv());
        assertEquals(1L, resultado.getUsuarioId());

        verify(veterinarianRepository).existsByCrmv("CRMV123");
        verify(userRepository).findById(1L);
        verify(veterinarianRepository).save(any(Veterinarian.class));
        verify(veterinarianMapper).toResponseDTO(veterinario);

    }

    @Test
    void LancarExcecaoQuandoCrmvJaEstiverCadastrado() {

        // arrange
        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .usuarioId(1L)
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(true);

        // act + assert
        CrmvAlreadyExistsException exception =
                assertThrows(CrmvAlreadyExistsException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("CRMV ja cadastrado", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoExistir() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .usuarioId(99L)
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());


        UserNotFoundException exception =
                assertThrows(UserNotFoundException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("User com o ID 99 não encontrado", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void BuscarVeterinarioPorIdComSucesso() {
        Veterinarian veterinario = Veterinarian.builder()
                .id(10L)
                .crmv("CRMV123")
                .usuario(User.builder().id(1L).build())
                .build();

        when(veterinarianRepository.findById(10L))
                .thenReturn(Optional.of(veterinario));

        when(veterinarianMapper.toResponseDTO(veterinario))
                .thenReturn(new VeterinarianResponse(10L, "CRMV123", 1L));

        VeterinarianResponse resultado =
                veterinarioService.getById(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("CRMV123", resultado.getCrmv());
        assertEquals(1L, resultado.getUsuarioId());
    }

    @Test
    void ListarTodosVeterinarios() {
        Veterinarian vet1 = Veterinarian.builder()
                .id(1L)
                .crmv("CRMV1")
                .usuario(User.builder().id(10L).build())
                .build();

        Veterinarian vet2 = Veterinarian.builder()
                .id(2L)
                .crmv("CRMV2")
                .usuario(User.builder().id(20L).build())
                .build();

        when(veterinarianRepository.findByUsuario_TipoUsuario(UserType.VETERINARIO))
                .thenReturn(List.of(vet1, vet2));

        when(veterinarianMapper.toResponseDTO(vet1))
                .thenReturn(new VeterinarianResponse(1L, "CRMV1", 10L));

        when(veterinarianMapper.toResponseDTO(vet2))
                .thenReturn(new VeterinarianResponse(2L, "CRMV2", 20L));

        List<VeterinarianResponse> resultado = veterinarioService.getAll();

        assertEquals(2, resultado.size());
    }

    @Test
    void AtualizarVeterinarioComSucesso() {

        Long veterinarioId = 10L;

        UpdateVeterinarianRequest dto =
                new UpdateVeterinarianRequest("CRMV999");

        Veterinarian veterinarioExistente = Veterinarian.builder()
                .id(veterinarioId)
                .crmv("CRMV123")
                .build();

        Veterinarian veterinarioAtualizado = Veterinarian.builder()
                .id(veterinarioId)
                .crmv("CRMV999")
                .build();

        VeterinarianResponse responseDTO =
                new VeterinarianResponse(10L, "CRMV999", null);

        when(veterinarianRepository.findById(veterinarioId))
                .thenReturn(Optional.of(veterinarioExistente));

        when(veterinarianRepository.save(any(Veterinarian.class)))
                .thenReturn(veterinarioAtualizado);

        when(veterinarianMapper.toResponseDTO(veterinarioAtualizado))
                .thenReturn(responseDTO);

        VeterinarianResponse resultado =
                veterinarioService.atualizar(veterinarioId, dto);

        assertNotNull(resultado);
        assertEquals("CRMV999", resultado.getCrmv());

        verify(veterinarianRepository).findById(veterinarioId);
        verify(veterinarianRepository).save(any(Veterinarian.class));
    }

    @Test
    void DeletarVeterinarioInexistente() {
        when(veterinarianRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(VeterinarianNotFoundException.class,
                () -> veterinarioService.deletar(99L));
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoForVeterinario() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .usuarioId(1L)
                .build();

        User usuario = User.builder()
                .id(1L)
                .userType(UserType.PROPRIETARIO) // NÃO é veterinário
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        InvalidUserTypeException exception =
                assertThrows(InvalidUserTypeException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("User não é do tipo VETERINARIO", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void LancarExcecaoQuandoUsuarioJaPossuirVeterinario() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .usuarioId(1L)
                .build();

        User usuario = User.builder()
                .id(1L)
                .userType(UserType.VETERINARIO)
                .veterinario(Veterinarian.builder().id(10L).build())
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UserAlreadyVeterinarianException exception =
                assertThrows(UserAlreadyVeterinarianException.class, () ->
                        veterinarioService.criarVeterinario(dto)
                );

        assertEquals("Usuário ja possui cadastro de veterinario", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }


}


