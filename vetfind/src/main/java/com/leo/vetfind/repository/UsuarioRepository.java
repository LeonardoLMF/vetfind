package com.leo.vetfind.repository;

import com.leo.vetfind.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //métodos inicias, futuramente sera implementado funcionalidades de acordo com o cliente

    //buscar usuario pelo email
    Optional<Usuario> findByEmail(String email);

    //verificar se o email ja existe
    boolean existsByEmail(String email);

}
