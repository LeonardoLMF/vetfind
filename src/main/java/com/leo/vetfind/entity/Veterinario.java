package com.leo.vetfind.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veterinarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Veterinario {

    /*acredito q seja melhor eu utilizar o mesmo Id para usuarios e veterinarios
      e ao inves de criar um ID unico para veterinarios, eu vejo qual o tipo do usuario */

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String crmv;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId //pega o mesmo id do usuario
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
