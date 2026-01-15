package com.leo.vetfind.dto.veterinarian;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VeterinarianResponse {

    private Long id;
    private String crmv;
    private Long userId;

}
