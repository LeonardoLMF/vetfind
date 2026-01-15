package com.leo.vetfind.service.veterinario;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioRequestDTO;
import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.dto.veterinario.UpdateVeterinarioRequestDTO;
import com.leo.vetfind.entity.User;
import com.leo.vetfind.entity.UserType;
import com.leo.vetfind.entity.Veterinarian;
import com.leo.vetfind.exception.*;
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
            throw new CrmvCadastradoException();
        }

        // verifica se o usuario existe
        User usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException(dto.getUsuarioId()));

        // verifica se o tipo do usuario é diferente de VETERINARIO
        if (usuario.getTipoUsuario() != UserType.VETERINARIO) {
            throw new TipoUsuarioInvalidoException();
        }

        // verifica se o usuario ja possui cadastro
        if (usuario.getVeterinario() != null) {
            throw new VeterinarioJaVinculadoException();
        }

        //cria uma entidade veterinario
        Veterinarian veterinario = Veterinarian.builder()
                .crmv(dto.getCrmv())
                .usuario(usuario)
                .build();

        // persiste
        Veterinarian salvo = veterinarioRepository.save(veterinario);

        return veterinarioMapper.toResponseDTO(salvo);

    }

    @Override
    public CadastroVeterinarioResponseDTO getById(Long id) {
        Veterinarian veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new VeterinarioNotFoundException(id));

        return veterinarioMapper.toResponseDTO(veterinario);
    }

    @Override
    public List<CadastroVeterinarioResponseDTO> getAll() {
        return veterinarioRepository
                .findByUsuario_TipoUsuario(UserType.VETERINARIO)
                .stream()
                .map(veterinarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CadastroVeterinarioResponseDTO atualizar(Long id, UpdateVeterinarioRequestDTO dto) {

        Veterinarian veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new VeterinarioNotFoundException(id));

        if (!veterinario.getCrmv().equals(dto.getCrmv())
                && veterinarioRepository.existsByCrmv(dto.getCrmv())) {
            throw new CrmvCadastradoException();
        }

        veterinario.setCrmv(dto.getCrmv());

        Veterinarian atualizado = veterinarioRepository.save(veterinario);

        return veterinarioMapper.toResponseDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {

        Veterinarian veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new VeterinarioNotFoundException(id));

        User usuario = veterinario.getUsuario();
        usuario.setVeterinario(null);

        veterinarioRepository.delete(veterinario);
    }

}