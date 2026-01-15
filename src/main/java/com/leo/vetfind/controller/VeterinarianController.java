package com.leo.vetfind.controller;

import com.leo.vetfind.dto.veterinarian.CreateVeterinarianRequest;
import com.leo.vetfind.dto.veterinarian.VeterinarianResponse;
import com.leo.vetfind.dto.veterinarian.UpdateVeterinarianRequest;
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
    public ResponseEntity<VeterinarianResponse> criarVeterinario(@Valid @RequestBody CreateVeterinarianRequest dto) {
        VeterinarianResponse response = veterinarioService.criarVeterinario(dto);
            return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarianResponse>> getAll() {
            return ResponseEntity.ok(veterinarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> getById(@PathVariable Long id) {
        VeterinarianResponse response = veterinarioService.getById(id);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarianResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UpdateVeterinarianRequest dto) {
        VeterinarianResponse response =
                veterinarioService.atualizar(id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
