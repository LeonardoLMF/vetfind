package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinarian.CreateVeterinarianRequest;
import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.dto.veterinarian.UpdateVeterinarianRequest;

import java.util.List;

public interface VeterinarianService {

    VeterinarianResponse createVeterinarian(CreateVeterinarianRequest dto);

    VeterinarianResponse findVeterinarianById(Long id);

    List<VeterinarianResponse> findAllVeterinarians();

    VeterinarianResponse updateVeterinarian(Long id, UpdateVeterinarianRequest dto);

    void deleteVeterinarian(Long id);

    List<VeterinarianResponse> searchByLocation(String city, String state);

    List<VeterinarianResponse> findNearby(Double latitude, Double longitude, Double radiusKm);
}
