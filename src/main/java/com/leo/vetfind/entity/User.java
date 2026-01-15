package com.leo.vetfind.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // Campos obrigatorios solicitado pelo cliente: email, senha, numero de telefone
    // e CASO o usuario for VETERINARIO ele precisa de uma CRMV tambem

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "senha")
    private String password;

    @Column(nullable = false, name = "telefone")
    private String phone;

    //determino se o usuario é um PROPRIETARIO ou VETERINARIO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo_usuario")
    private UserType userType;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Veterinarian veterinarian;
}
