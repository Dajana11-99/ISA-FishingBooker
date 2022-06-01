package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AvailableInstructorPeriodService {

    Set<AvailableInstructorPeriod> getAvailablePeriod(String username);

    boolean setAvailableInstructorPeriod(AvailableInstructorPeriod availableInstructorPeriod);

    boolean instructorIsAvailable(Long id, LocalDateTime startDate, LocalDateTime endDate);

    boolean deleteAvailableBoatsPeriod(AvailableInstructorPeriod availableInstructorPeriod);

    boolean editAvailableInstructorsPeriod(AvailableInstructorPeriod oldPeriod, AvailableInstructorPeriod newPeriod);

    List<Long> getAvailableFishingInstructorsIdsForPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
