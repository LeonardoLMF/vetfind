package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioRequestDTO;
import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.entity.TipoUsuario;
import com.leo.vetfind.entity.usuario.Usuario;
import com.leo.vetfind.entity.veterinario.Veterinario;
import com.leo.vetfind.mapper.VeterinarioMapper;
import com.leo.vetfind.repository.UsuarioRepository;
import com.leo.vetfind.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements VeterinarioService{

    private final VeterinarioRepository veterinarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeterinarioMapper veterinarioMapper;

    @Override
    public CadastroVeterinarioResponseDTO criarVeterinario(CadastroVeterinarioRequestDTO dto) {

        // verifica se ja existe alg registrado com a crmv
        if (veterinarioRepository.existsByCrmv(dto.getCrmv())) {
            throw new IllegalArgumentException("CRMV já cadastrado.");
        }

        // verifica se o usuario existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // verifica se o tipo do usuario é diferente de VETERINARIO
        if (usuario.getTipoUsuario() != TipoUsuario.VETERINARIO) {
            throw new IllegalArgumentException("Usuário não é do tipo VETERINARIO.");
        }

        // verifica se o usuario ja possui cadastro
        if (usuario.getVeterinario() != null) {
            throw new IllegalArgumentException("Usuário já possui cadastro de veterinário.");
        }

        //cria uma entidade veterinario
        Veterinario veterinario = Veterinario.builder()
                .crmv(dto.getCrmv())
                .usuario(usuario)
                .build();

        // persiste
        Veterinario salvo = veterinarioRepository.save(veterinario);

        return veterinarioMapper.toResponseDTO(salvo);

    }

    @Override
    public CadastroVeterinarioResponseDTO getById(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinário não encontrado."));

        return veterinarioMapper.toResponseDTO(veterinario);
    }

    @Override
    public List<CadastroVeterinarioResponseDTO> getAll() {
        return veterinarioRepository
                .findByUsuario_TipoUsuario(TipoUsuario.VETERINARIO)
                .stream()
                .map(veterinarioMapper::toResponseDTO)
                .toList();
    }
}