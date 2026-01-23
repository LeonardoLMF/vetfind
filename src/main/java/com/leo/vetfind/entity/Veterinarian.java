package com.leo.vetfind.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veterinarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Veterinarian {

    /*acredito q seja melhor eu utilizar o mesmo Id para usuarios e veterinarios
      e ao inves de criar um ID unico para veterinarios, eu vejo qual o tipo do usuario */

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String crmv;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId //pega o mesmo id do usuario
    @JoinColumn(name = "usuario_id")
    private User user;

    @OneToMany(mappedBy = "veterinarian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();
}
