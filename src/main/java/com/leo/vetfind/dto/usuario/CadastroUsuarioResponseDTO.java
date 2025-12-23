package com.leo.vetfind.dto.usuario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastroUsuarioResponseDTO {
    //não passar a senha pois é sensivel

    private Long id;
    private String email;
    private String telefone;
    private String tipoUsuario;
}
