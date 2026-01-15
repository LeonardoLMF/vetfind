package com.leo.vetfind.dto.usuario;


import com.leo.vetfind.entity.UserType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastroUsuarioRequestDTO {

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O E-mail é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5)
    private String senha;

    @NotBlank(message = "O numero de telefone é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O numero de telefone deve ter 11 dígitos") // regex pego do stackoverflow
    private String telefone;

    @NotNull(message = "O tipo do usuário é obrigatório")
    private UserType userType;
}
