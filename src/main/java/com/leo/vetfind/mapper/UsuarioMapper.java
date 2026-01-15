package com.leo.vetfind.mapper;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // dto > entity
    User toEntity(CreateUserRequest dto);


    //entity > dto
    UserResponse toResponseDTO(User usuario);

    //lista
    List<UserResponse> toResponseDTOList (List<User> usuarios);
}
