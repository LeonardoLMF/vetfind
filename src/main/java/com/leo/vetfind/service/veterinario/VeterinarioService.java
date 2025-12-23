package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioRequestDTO;
import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.entity.usuario.Usuario;
import com.leo.vetfind.entity.veterinario.Veterinario;
import com.leo.vetfind.repository.UsuarioRepository;
import com.leo.vetfind.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final UsuarioRepository usuarioRepository;

    public CadastroVeterinarioResponseDTO criarVeterinario(CadastroVeterinarioRequestDTO dto) {

        // verifica se ja existe alg registrado com a crmv
        if (veterinarioRepository.existsByCrmv(dto.getCrmv())) {
            throw new IllegalArgumentException("CRMV já cadastrado.");
        }

        // verifica se o usuario existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        //cria uma entidade vet
        Veterinario veterinario = Veterinario.builder()
                .crmv(dto.getCrmv())
                .usuario(usuario)
                .build();

        // persiste
        Veterinario salvo = veterinarioRepository.save(veterinario);

        // retorna o dto resposta
        return CadastroVeterinarioResponseDTO.builder()
                .id(salvo.getId())
                .crmv(salvo.getCrmv())
                .usuarioId(salvo.getUsuario().getId())
                .build();
    }
}