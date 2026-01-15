package com.leo.vetfind.controller;

import com.leo.vetfind.dto.usuario.CadastroUsuarioRequestDTO;
import com.leo.vetfind.dto.usuario.CadastroUsuarioResponseDTO;
import com.leo.vetfind.dto.usuario.UpdateUsuarioRequestDTO;
import com.leo.vetfind.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<CadastroUsuarioResponseDTO> criarUsuario (@Valid @RequestBody CadastroUsuarioRequestDTO dto) {
        CadastroUsuarioResponseDTO response = usuarioService.criarUsuario(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CadastroUsuarioResponseDTO>> listarUsuarios(){
        List<CadastroUsuarioResponseDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CadastroUsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        CadastroUsuarioResponseDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CadastroUsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UpdateUsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
