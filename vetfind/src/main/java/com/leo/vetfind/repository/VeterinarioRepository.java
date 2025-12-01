package com.leo.vetfind.repository;

import com.leo.vetfind.entity.veterinario.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Optional<Veterinario> findByCrmv (String crmv);

    boolean existsByCrmv(String crmv);

}
