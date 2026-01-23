package com.leo.vetfind.service.availability;

import com.leo.vetfind.dto.availability.AvailabilityResponse;
import com.leo.vetfind.dto.availability.CreateAvailabilityRequest;
import com.leo.vetfind.dto.availability.UpdateAvailabilityRequest;
import com.leo.vetfind.entity.Availability;
import com.leo.vetfind.entity.Veterinarian;
import com.leo.vetfind.exception.AvailabilityConflictException;
import com.leo.vetfind.exception.AvailabilityNotFoundException;
import com.leo.vetfind.exception.InvalidTimeRangeException;
import com.leo.vetfind.exception.VeterinarianNotFoundException;
import com.leo.vetfind.mapper.AvailabilityMapper;
import com.leo.vetfind.repository.AvailabilityRepository;
import com.leo.vetfind.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final AvailabilityMapper availabilityMapper;

    @Override
    @Transactional
    public AvailabilityResponse createAvailability(Long veterinarianId, CreateAvailabilityRequest dto) {
        log.info("Creating availability for veterinarian ID: {}", veterinarianId);

        // Validate veterinarian exists
        Veterinarian veterinarian = veterinarianRepository.findById(veterinarianId)
                .orElseThrow(() -> new VeterinarianNotFoundException(veterinarianId));

        // Validate time range
        validateTimeRange(dto.getStartTime(), dto.getEndTime());

        // Check for conflicts
        checkForConflicts(veterinarianId, dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime(), null);

        // Create availability
        Availability availability = availabilityMapper.toEntity(dto);
        availability.setVeterinarian(veterinarian);
        availability.setActive(true);

        Availability saved = availabilityRepository.save(availability);
        log.info("Availability created successfully with ID: {}", saved.getId());

        return availabilityMapper.toResponseDTO(saved);
    }

    @Override
    public List<AvailabilityResponse> findAllByVeterinarian(Long veterinarianId) {
        log.info("Finding all availabilities for veterinarian ID: {}", veterinarianId);

        // Validate veterinarian exists
        if (!veterinarianRepository.existsById(veterinarianId)) {
            throw new VeterinarianNotFoundException(veterinarianId);
        }

        List<Availability> availabilities = availabilityRepository
                .findByVeterinarianIdOrderByDayOfWeekAscStartTimeAsc(veterinarianId);

        log.info("Found {} availabilities for veterinarian ID: {}", availabilities.size(), veterinarianId);

        return availabilityMapper.toResponseDTOList(availabilities);
    }

    @Override
    public AvailabilityResponse findById(Long id) {
        log.info("Finding availability by ID: {}", id);

        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AvailabilityNotFoundException(id));

        return availabilityMapper.toResponseDTO(availability);
    }

    @Override
    @Transactional
    public AvailabilityResponse updateAvailability(Long id, UpdateAvailabilityRequest dto) {
        log.info("Updating availability ID: {}", id);

        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AvailabilityNotFoundException(id));

        // Validate time range
        validateTimeRange(dto.getStartTime(), dto.getEndTime());

        // Check for conflicts (excluding current availability)
        checkForConflicts(
                availability.getVeterinarian().getId(),
                dto.getDayOfWeek(),
                dto.getStartTime(),
                dto.getEndTime(),
                id
        );

        // Update fields
        availability.setDayOfWeek(dto.getDayOfWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setActive(dto.getActive());

        Availability updated = availabilityRepository.save(availability);
        log.info("Availability updated successfully with ID: {}", updated.getId());

        return availabilityMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteAvailability(Long id) {
        log.info("Deleting availability ID: {}", id);

        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AvailabilityNotFoundException(id));

        availabilityRepository.delete(availability);
        log.info("Availability deleted successfully with ID: {}", id);
    }

    private void validateTimeRange(java.time.LocalTime startTime, java.time.LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new InvalidTimeRangeException();
        }
    }

    private void checkForConflicts(Long veterinarianId, java.time.DayOfWeek dayOfWeek,
                                   java.time.LocalTime startTime, java.time.LocalTime endTime,
                                   Long excludeId) {
        List<Availability> conflicts;

        if (excludeId != null) {
            conflicts = availabilityRepository.findConflictingAvailabilities(
                    veterinarianId, dayOfWeek, startTime, endTime, excludeId
            );
        } else {
            conflicts = availabilityRepository.findConflictingAvailabilitiesForNew(
                    veterinarianId, dayOfWeek, startTime, endTime
            );
        }

        if (!conflicts.isEmpty()) {
            log.warn("Availability conflict detected for veterinarian ID: {}", veterinarianId);
            throw new AvailabilityConflictException();
        }
    }
}