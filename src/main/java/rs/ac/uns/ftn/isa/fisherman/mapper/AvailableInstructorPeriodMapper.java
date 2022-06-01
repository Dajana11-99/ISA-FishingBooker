package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AvailablePeriodDto;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;

import java.util.HashSet;
import java.util.Set;

public class AvailableInstructorPeriodMapper {

    public AvailablePeriodDto availableInstructorPeriodToAvailablePeriodDto(AvailableInstructorPeriod availableInstructorPeriod){
        return new AvailablePeriodDto(availableInstructorPeriod.getId(),availableInstructorPeriod.getStartDate(),
                availableInstructorPeriod.getEndDate(),availableInstructorPeriod.getFishingInstructor().getUsername());
    }
    public Set<AvailablePeriodDto> availableInstructorPeriodsToDtoS(Set<AvailableInstructorPeriod> availableInstructorPeriods){
        Set<AvailablePeriodDto> periods = new HashSet<>();
        for(AvailableInstructorPeriod availableInstructorPeriod: availableInstructorPeriods){
            periods.add(availableInstructorPeriodToAvailablePeriodDto(availableInstructorPeriod));
        }
        return periods;
    }
    public  AvailableInstructorPeriod availablePeriodDtoToAvailableInstructorPeriod(AvailablePeriodDto availablePeriodDto, FishingInstructor fishingInstructor){
        return  new AvailableInstructorPeriod(availablePeriodDto.getId(), availablePeriodDto.getStartDate(), availablePeriodDto.getEndDate(),fishingInstructor);
    }
}
