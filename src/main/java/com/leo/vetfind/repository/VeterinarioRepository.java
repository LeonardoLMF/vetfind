package com.leo.vetfind.repository;

import com.leo.vetfind.entity.UserType;
import com.leo.vetfind.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinarian, Long> {

    Optional<Veterinarian> findByCrmv (String crmv);
    boolean existsByCrmv(String crmv);
    List<Veterinarian> findByUsuario_TipoUsuario(UserType tipoUsuario);

}
