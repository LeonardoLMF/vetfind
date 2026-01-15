package com.leo.vetfind.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    //não passar a senha pois é sensivel

    private Long id;
    private String email;
    private String phone;
    private String userType;
}
