package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.appointment.AppointmentResponse;
import com.leo.vetfind.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.email", target = "ownerEmail")
    @Mapping(source = "owner.phone", target = "ownerPhone")
    @Mapping(source = "veterinarian.id", target = "veterinarianId")
    @Mapping(source = "veterinarian.user.email", target = "veterinarianEmail")
    @Mapping(source = "veterinarian.user.phone", target = "veterinarianPhone")
    @Mapping(source = "veterinarian.crmv", target = "veterinarianCrmv")
    AppointmentResponse toResponseDTO(Appointment appointment);

    List<AppointmentResponse> toResponseDTOList(List<Appointment> appointments);
}