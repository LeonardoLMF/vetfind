package com.leo.vetfind.dto.veterinario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastroVeterinarioRequestDTO {

    @NotBlank(message = "CRMV é obrigatorio")
    private String crmv;

    @NotNull(message = "O Id de usuário é obrigatório")
    private Long usuarioId;
}
