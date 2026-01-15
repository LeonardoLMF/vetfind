package com.leo.vetfind.controller;

import com.leo.vetfind.dto.veterinario.CadastroVeterinarioRequestDTO;
import com.leo.vetfind.dto.veterinario.CadastroVeterinarioResponseDTO;
import com.leo.vetfind.dto.veterinario.UpdateVeterinarioRequestDTO;
import com.leo.vetfind.service.veterinario.VeterinarioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarianController {

    private final VeterinarioServiceImpl veterinarioService;

    @PostMapping
    public ResponseEntity<CadastroVeterinarioResponseDTO> criarVeterinario(@Valid @RequestBody CadastroVeterinarioRequestDTO dto) {
        CadastroVeterinarioResponseDTO response = veterinarioService.criarVeterinario(dto);
            return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CadastroVeterinarioResponseDTO>> getAll() {
            return ResponseEntity.ok(veterinarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CadastroVeterinarioResponseDTO> getById(@PathVariable Long id) {
        CadastroVeterinarioResponseDTO response = veterinarioService.getById(id);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CadastroVeterinarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UpdateVeterinarioRequestDTO dto) {
        CadastroVeterinarioResponseDTO response =
                veterinarioService.atualizar(id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
