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
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeterinarianServiceImplTest {

    @Mock
    private VeterinarianRepository veterinarianRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VeterinarianMapper veterinarianMapper;
    @InjectMocks
    private VeterinarianServiceImpl veterinarianService;

    @Test
    void CriarVeterinarioComSucesso() {
        //giv
        CreateVeterinarianRequest request = new CreateVeterinarianRequest(
                "CRMV123",
                1L
        );

        User user = User.builder()
                .id(1L)
                .userType(UserType.VETERINARIO)
                .build();

        Veterinarian veterinario = Veterinarian.builder()
                .id(10L)
                .crmv("CRMV123")
                .user(user)
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
                .thenReturn(java.util.Optional.of(user));

        when(veterinarianRepository.save(any(Veterinarian.class)))
                .thenReturn(veterinario);

        when(veterinarianMapper.toResponseDTO(veterinario))
                .thenReturn(responseEsperado);

        //wh
        VeterinarianResponse result =
                veterinarianService.createVeterinarian(request);

        //th
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("CRMV123", result.getCrmv());
        assertEquals(1L, result.getUserId());

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
                .userId(1L)
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(true);

        // act + assert
        CrmvAlreadyExistsException exception =
                assertThrows(CrmvAlreadyExistsException.class, () ->
                        veterinarianService.createVeterinarian(dto)
                );

        assertEquals("CRMV ja cadastrado", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoExistir() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .userId(99L)
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());


        UserNotFoundException exception =
                assertThrows(UserNotFoundException.class, () ->
                        veterinarianService.createVeterinarian(dto)
                );

        assertEquals("User com o ID 99 não encontrado", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void BuscarVeterinarioPorIdComSucesso() {
        Veterinarian veterinario = Veterinarian.builder()
                .id(10L)
                .crmv("CRMV123")
                .user(User.builder().id(1L).build())
                .build();

        when(veterinarianRepository.findById(10L))
                .thenReturn(Optional.of(veterinario));

        when(veterinarianMapper.toResponseDTO(veterinario))
                .thenReturn(new VeterinarianResponse(10L, "CRMV123", 1L));

        VeterinarianResponse resultado =
                veterinarianService.findVeterinarianById(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("CRMV123", resultado.getCrmv());
        assertEquals(1L, resultado.getUserId());
    }

    @Test
    void ListarTodosVeterinarios() {
        Veterinarian vet1 = Veterinarian.builder()
                .id(1L)
                .crmv("CRMV1")
                .user(User.builder().id(10L).build())
                .build();

        Veterinarian vet2 = Veterinarian.builder()
                .id(2L)
                .crmv("CRMV2")
                .user(User.builder().id(20L).build())
                .build();

        when(veterinarianRepository.findByUser_UserType(UserType.VETERINARIO, Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(List.of(vet1, vet2));

        when(veterinarianMapper.toResponseDTO(vet1))
                .thenReturn(new VeterinarianResponse(1L, "CRMV1", 10L));

        when(veterinarianMapper.toResponseDTO(vet2))
                .thenReturn(new VeterinarianResponse(2L, "CRMV2", 20L));

        List<VeterinarianResponse> resultado = veterinarianService.findAllVeterinarians();

        assertEquals(2, resultado.size());
    }

    @Test
    void AtualizarVeterinarioComSucesso() {

        Long veterinarioId = 10L;

        UpdateVeterinarianRequest dto =
                new UpdateVeterinarianRequest("CRMV999");

        Veterinarian beforeUpdateVeterinarian = Veterinarian.builder()
                .id(veterinarioId)
                .crmv("CRMV123")
                .build();

        Veterinarian afterUpdateVeterinarian = Veterinarian.builder()
                .id(veterinarioId)
                .crmv("CRMV999")
                .build();

        VeterinarianResponse responseDTO =
                new VeterinarianResponse(10L, "CRMV999", null);

        when(veterinarianRepository.findById(veterinarioId))
                .thenReturn(Optional.of(beforeUpdateVeterinarian));

        when(veterinarianRepository.save(any(Veterinarian.class)))
                .thenReturn(afterUpdateVeterinarian);

        when(veterinarianMapper.toResponseDTO(afterUpdateVeterinarian))
                .thenReturn(responseDTO);

        VeterinarianResponse result =
                veterinarianService.updateVeterinarian(veterinarioId, dto);

        assertNotNull(result);
        assertEquals("CRMV999", result.getCrmv());

        verify(veterinarianRepository).findById(veterinarioId);
        verify(veterinarianRepository).save(any(Veterinarian.class));
    }

    @Test
    void DeletarVeterinarioInexistente() {
        when(veterinarianRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(VeterinarianNotFoundException.class,
                () -> veterinarianService.deleteVeterinarian(99L));
    }

    @Test
    void LancarExcecaoQuandoUsuarioNaoForVeterinario() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .userId(1L)
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
                        veterinarianService.createVeterinarian(dto)
                );

        assertEquals("User não é do tipo VETERINARIO", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }

    @Test
    void LancarExcecaoQuandoUsuarioJaPossuirVeterinario() {

        CreateVeterinarianRequest dto = CreateVeterinarianRequest.builder()
                .crmv("CRMV123")
                .userId(1L)
                .build();

        User usuario = User.builder()
                .id(1L)
                .userType(UserType.VETERINARIO)
                .veterinarian(Veterinarian.builder().id(10L).build())
                .build();

        when(veterinarianRepository.existsByCrmv("CRMV123"))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UserAlreadyVeterinarianException exception =
                assertThrows(UserAlreadyVeterinarianException.class, () ->
                        veterinarianService.createVeterinarian(dto)
                );

        assertEquals("Usuário ja possui cadastro de veterinario", exception.getMessage());

        verify(veterinarianRepository, never()).save(any());
    }


}


