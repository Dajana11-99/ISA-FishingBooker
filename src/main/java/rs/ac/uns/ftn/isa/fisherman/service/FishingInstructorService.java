package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.dto.AvailableInstructorPeriodDto;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;

import java.util.List;
import java.util.Set;

public interface FishingInstructorService {
    List<FishingInstructor> getNewFishingInstructors();
    List<FishingInstructor> getNewActiveInstructors();

    FishingInstructor findByUsername(String fishingInstructorUsername);


    FishingInstructor findByID(Long id);

    void save(FishingInstructor fishingInstructor);
}
