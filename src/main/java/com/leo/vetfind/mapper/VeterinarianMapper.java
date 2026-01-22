package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.entity.Veterinarian;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {UserMapper.class})
public interface VeterinarianMapper {

    // entity > dto
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.address", target = "address")
    @Mapping(source = "user.latitude", target = "latitude")
    @Mapping(source = "user.longitude", target = "longitude")
    VeterinarianResponse toResponseDTO(Veterinarian veterinarian);

    List<VeterinarianResponse> toResponseDTOList(List<Veterinarian> veterinarians);


}
