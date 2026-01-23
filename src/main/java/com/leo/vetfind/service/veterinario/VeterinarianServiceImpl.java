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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class VeterinarianServiceImpl implements VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;
    private final UserRepository userRepository;
    private final VeterinarianMapper veterinarianMapper;

    @Override
    @Transactional
    public VeterinarianResponse createVeterinarian(CreateVeterinarianRequest dto) {

        // verifica se ja existe alg registrado com a crmv
        if (veterinarianRepository.existsByCrmv(dto.getCrmv())) {
            throw new CrmvAlreadyExistsException();
        }

        // verifica se o usuario existe
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId()));

        // verifica se o tipo do usuario é diferente de VETERINARIO
        if (user.getUserType() != UserType.VETERINARIAN) {
            throw new InvalidUserTypeException();
        }

        // verifica se o usuario ja possui cadastro
        if (user.getVeterinarian() != null) {
            throw new UserAlreadyVeterinarianException();
        }

        //cria uma entidade veterinario
        Veterinarian veterinarian = Veterinarian.builder()
                .crmv(dto.getCrmv())
                .user(user)
                .build();

        // persiste
        Veterinarian saved = veterinarianRepository.save(veterinarian);
        return veterinarianMapper.toResponseDTO(saved);
    }

    @Override
    public VeterinarianResponse findVeterinarianById(Long id) {
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));
        return veterinarianMapper.toResponseDTO(veterinarian);
    }

    @Override
    public List<VeterinarianResponse> findAllVeterinarians() {
        return veterinarianRepository
                .findByUser_UserType(UserType.VETERINARIAN, Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(veterinarianMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public VeterinarianResponse updateVeterinarian(Long id, UpdateVeterinarianRequest dto) {

        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));

        if (!veterinarian.getCrmv().equals(dto.getCrmv())
                && veterinarianRepository.existsByCrmv(dto.getCrmv())) {
            throw new CrmvAlreadyExistsException();
        }

        veterinarian.setCrmv(dto.getCrmv());

        Veterinarian updated = veterinarianRepository.save(veterinarian);

        return veterinarianMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteVeterinarian(Long id) {
        Veterinarian veterinarian = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VeterinarianNotFoundException(id));

        User usuario = veterinarian.getUser();
        usuario.setVeterinarian(null);

        veterinarianRepository.delete(veterinarian);
    }

    @Override
    public List<VeterinarianResponse> searchByLocation(String city, String state) {
        log.info("Searching veterinarians - city: {}, state: {}", city, state);

        List<Veterinarian> veterinarians;

        if (city != null && state != null) {
            // Busca por cidade E estado
            veterinarians = veterinarianRepository.findByCityAndState(city, state);
        } else if (city != null) {
            // Busca apenas por cidade
            veterinarians = veterinarianRepository.findByCity(city);
        } else if (state != null) {
            // Busca apenas por estado
            veterinarians = veterinarianRepository.findByState(state);
        } else {
            // Se não passou nenhum filtro, retorna todos
            veterinarians = veterinarianRepository.findByUser_UserType(UserType.VETERINARIAN, Sort.by(Sort.Direction.ASC, "id"));
        }

        log.info("Found {} veterinarians", veterinarians.size());

        return veterinarians.stream()
                .map(veterinarianMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<VeterinarianResponse> findNearby(Double latitude, Double longitude, Double radiusKm) {
        log.info("Searching veterinarians nearby - lat: {}, lng: {}, radius: {}km",
                latitude, longitude, radiusKm);

        List<Veterinarian> veterinarians = veterinarianRepository.findNearby(latitude, longitude, radiusKm);

        log.info("Found {} veterinarians within {}km", veterinarians.size(), radiusKm);

        return veterinarians.stream()
                .map(veterinarianMapper::toResponseDTO)
                .toList();
    }
}