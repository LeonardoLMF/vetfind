package com.leo.vetfind.repository;

import com.leo.vetfind.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByVeterinarianIdOrderByDayOfWeekAscStartTimeAsc(Long veterinarianId);

    List<Availability> findByVeterinarianIdAndActiveTrue(Long veterinarianId);

    @Query("""
        SELECT a FROM Availability a 
        WHERE a.veterinarian.id = :veterinarianId 
        AND a.dayOfWeek = :dayOfWeek 
        AND a.id != :excludeId
        AND a.active = true
        AND (
            (a.startTime < :endTime AND a.endTime > :startTime)
        )
    """)
    List<Availability> findConflictingAvailabilities(
            @Param("veterinarianId") Long veterinarianId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludeId") Long excludeId
    );

    @Query("""
        SELECT a FROM Availability a 
        WHERE a.veterinarian.id = :veterinarianId 
        AND a.dayOfWeek = :dayOfWeek 
        AND a.active = true
        AND (
            (a.startTime < :endTime AND a.endTime > :startTime)
        )
    """)
    List<Availability> findConflictingAvailabilitiesForNew(
            @Param("veterinarianId") Long veterinarianId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}