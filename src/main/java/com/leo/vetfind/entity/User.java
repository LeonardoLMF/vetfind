package com.leo.vetfind.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
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

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "phone")
    private String phone;

    //determino se o usuario é um PROPRIETARIO ou VETERINARIO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_type")
    private UserType userType;

    @Embedded
    private Address address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Veterinarian veterinarian;
}
