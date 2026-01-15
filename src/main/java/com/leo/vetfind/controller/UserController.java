package com.leo.vetfind.controller;

import com.leo.vetfind.dto.user.CreateUserRequest;
import com.leo.vetfind.dto.user.UserResponse;
import com.leo.vetfind.dto.user.UpdateUserRequest;
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
    public ResponseEntity<UserResponse> criarUsuario (@Valid @RequestBody CreateUserRequest dto) {
        UserResponse response = usuarioService.criarUsuario(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listarUsuarios(){
        List<UserResponse> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarUsuarioPorId(@PathVariable Long id) {
        UserResponse usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizar(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
