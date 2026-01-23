package com.leo.vetfind.repository;

import com.leo.vetfind.entity.UserType;
import com.leo.vetfind.entity.Veterinarian;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    Optional<Veterinarian> findByCrmv (String crmv);

    boolean existsByCrmv(String crmv);

    List<Veterinarian> findByUser_UserType(UserType userType, Sort sort);

    @Query("SELECT v FROM Veterinarian v WHERE v.user.address.city = :city ORDER BY v.id ASC")
    List<Veterinarian> findByCity(@Param("city") String city);

    @Query("SELECT v FROM Veterinarian v WHERE v.user.address.state = :state ORDER BY v.id ASC")
    List<Veterinarian> findByState(@Param("state") String state);

    @Query("SELECT v FROM Veterinarian v WHERE v.user.address.city = :city AND v.user.address.state = :state ORDER BY v.id ASC")
    List<Veterinarian> findByCityAndState(@Param("city") String city, @Param("state") String state);

    // haversine formula for calculate distances between geo cordinates (basically a findByProximity)
    @Query(value = """
        SELECT v.* FROM veterinarians v
        INNER JOIN users u ON v.user_id = u.id
        WHERE u.latitude IS NOT NULL 
        AND u.longitude IS NOT NULL
        AND u.user_type = 'VETERINARIAN'
        AND (6371 * acos(
            LEAST(1.0, GREATEST(-1.0,
                cos(radians(:latitude)) 
                * cos(radians(u.latitude)) 
                * cos(radians(u.longitude) - radians(:longitude)) 
                + sin(radians(:latitude)) 
                * sin(radians(u.latitude))
            ))
        )) <= :radiusKm
        ORDER BY (6371 * acos(
            LEAST(1.0, GREATEST(-1.0,
                cos(radians(:latitude)) 
                * cos(radians(u.latitude)) 
                * cos(radians(u.longitude) - radians(:longitude)) 
                + sin(radians(:latitude)) 
                * sin(radians(u.latitude))
            ))
        )) ASC
        """, nativeQuery = true)
    List<Veterinarian> findNearby(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radiusKm") Double radiusKm
    );

}
