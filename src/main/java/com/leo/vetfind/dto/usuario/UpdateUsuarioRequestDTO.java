package com.leo.vetfind.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUsuarioRequestDTO {

    @Email(message = "E-mail inválido ")
    @NotBlank(message = "O E-mail é obrigatorio")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O telefone deve ter 11 dígitos")
    private String telefone;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres")
    private String senha;

}
