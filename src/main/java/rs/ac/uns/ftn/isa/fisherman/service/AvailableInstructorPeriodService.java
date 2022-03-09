package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;

import java.util.Set;

public interface AvailableInstructorPeriodService {


    Set<AvailableInstructorPeriod> getAvailablePeriod(String username);

    void setAvailableInstructorPeriod(Long id, Set<AvailableInstructorPeriod> availableInstructorPeriod);
}
