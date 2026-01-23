package com.leo.vetfind.service.availability;

import com.leo.vetfind.dto.availability.AvailabilityResponse;
import com.leo.vetfind.dto.availability.CreateAvailabilityRequest;
import com.leo.vetfind.dto.availability.UpdateAvailabilityRequest;

import java.util.List;

public interface AvailabilityService {

    AvailabilityResponse createAvailability(Long veterinarianId, CreateAvailabilityRequest dto);

    List<AvailabilityResponse> findAllByVeterinarian(Long veterinarianId);

    AvailabilityResponse findById(Long id);

    AvailabilityResponse updateAvailability(Long id, UpdateAvailabilityRequest dto);

    void deleteAvailability(Long id);
}