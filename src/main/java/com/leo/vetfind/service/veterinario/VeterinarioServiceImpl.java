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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements VeterinarioService{

    private final VeterinarianRepository veterinarianRepository;
    private final UserRepository userRepository;
    private final VeterinarianMapper veterinarianMapper;

    @Override
    public VeterinarianResponse criarVeterinario(CreateVeterinarianRequest dto) {

        // verifica se ja existe alg registrado com a crmv
        if (veterinarianRepository.existsByCrmv(dto.getCrmv())) {
            throw new CrmvAlreadyExistsException();
        }

        // verifica se o usuario existe
        User usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUsuarioId()));

        // verifica se o tipo do usuario é diferente de VETERINARIO
        if (usuario.getTipoUsuario() != UserType.VETERINARIO) {
            throw new InvalidUserTypeException();
        }

        // verifica se o usuario ja possui cadastro
        if (usuario.getVeterinario() != null) {
            throw new UserAlreadyVeterinarianException();
        }

        //cria uma entidade veterinario
        Veterinarian veterinario = Veterinarian.builder()
                .crmv(dto.getCrmv())
                .usuario(usuario)
                .build();

        // persiste
        Veterinarian salvo = veterinarianRepository.save(veterinario);

        return veterinarianMapper.toResponseDTO(salvo);

    }

    @Override
    public VeterinarianResponse getById(Long id) {
        Veterinarian veterinario = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));

        return veterinarianMapper.toResponseDTO(veterinario);
    }

    @Override
    public List<VeterinarianResponse> getAll() {
        return veterinarianRepository
                .findByUsuario_TipoUsuario(UserType.VETERINARIO)
                .stream()
                .map(veterinarianMapper::toResponseDTO)
                .toList();
    }

    @Override
    public VeterinarianResponse atualizar(Long id, UpdateVeterinarianRequest dto) {

        Veterinarian veterinario = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));

        if (!veterinario.getCrmv().equals(dto.getCrmv())
                && veterinarianRepository.existsByCrmv(dto.getCrmv())) {
            throw new CrmvAlreadyExistsException();
        }

        veterinario.setCrmv(dto.getCrmv());

        Veterinarian atualizado = veterinarianRepository.save(veterinario);

        return veterinarianMapper.toResponseDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {

        Veterinarian veterinario = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));

        User usuario = veterinario.getUsuario();
        usuario.setVeterinario(null);

        veterinarianRepository.delete(veterinario);
    }

}