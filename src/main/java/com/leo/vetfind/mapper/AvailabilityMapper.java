package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.availability.AvailabilityResponse;
import com.leo.vetfind.dto.availability.CreateAvailabilityRequest;
import com.leo.vetfind.entity.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    Availability toEntity(CreateAvailabilityRequest dto);

    @Mapping(source = "veterinarian.id", target = "veterinarianId")
    AvailabilityResponse toResponseDTO(Availability availability);

    List<AvailabilityResponse> toResponseDTOList(List<Availability> availabilities);
}