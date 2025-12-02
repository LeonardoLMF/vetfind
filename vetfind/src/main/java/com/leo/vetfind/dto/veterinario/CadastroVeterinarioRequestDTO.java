package com.leo.vetfind.dto.veterinario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastroVeterinarioRequestDTO {

    @NotBlank(message = "CRMV é obrigatorio")
    private String crmv;

    @NotBlank(message = "O Id de usuário é obrigatório")
    private Long usuarioId;
}
