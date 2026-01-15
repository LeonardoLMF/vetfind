package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinarian.CreateVeterinarianRequest;
import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.dto.veterinarian.UpdateVeterinarianRequest;

import java.util.List;

public interface VeterinarioService {

    VeterinarianResponse criarVeterinario(CreateVeterinarianRequest dto);

    VeterinarianResponse getById(Long id);

    List<VeterinarianResponse> getAll();

    VeterinarianResponse atualizar(Long id, UpdateVeterinarianRequest dto);

    void deletar (Long id);
}
