package com.leo.vetfind.entity.usuario;

import com.leo.vetfind.entity.TipoUsuario;
import com.leo.vetfind.entity.veterinario.Veterinario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    // Campos obrigatorios solicitado pelo cliente: email, senha, numero de telefone
    // e CASO o usuario for VETERINARIO ele precisa de uma CRMV tambem

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    //determino se o usuario é um PROPRIETARIO ou VETERINARIO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Veterinario veterinario;
}
