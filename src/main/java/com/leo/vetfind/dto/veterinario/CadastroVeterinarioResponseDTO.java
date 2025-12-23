package com.leo.vetfind.dto.veterinario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastroVeterinarioResponseDTO {

    private Long id;
    private String crmv;
    private Long usuarioId;

}
