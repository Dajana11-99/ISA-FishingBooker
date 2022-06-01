package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AvailablePeriodDto;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableBoatPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;

public class AvailableBoatPeriodMapper {
    public AvailablePeriodDto availableBoatPeriodToAvailablePeriodDto(AvailableBoatPeriod availableBoatPeriod){
        return new AvailablePeriodDto(availableBoatPeriod.getId(),availableBoatPeriod.getStartDate(),
                availableBoatPeriod.getEndDate(),null,availableBoatPeriod.getBoat().getId());
    }
    public  AvailableBoatPeriod availablePeriodDtoToAvailableBoatPeriod(AvailablePeriodDto availablePeriodDto, Boat boat){
        return  new AvailableBoatPeriod(availablePeriodDto.getId(), availablePeriodDto.getStartDate(), availablePeriodDto.getEndDate(),boat);
    }

}
