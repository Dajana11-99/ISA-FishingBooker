package rs.ac.uns.ftn.isa.fisherman.mapper;
import rs.ac.uns.ftn.isa.fisherman.dto.AvailablePeriodDto;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableCabinPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;

public class AvailableCabinPeriodMapper {
    public AvailablePeriodDto availableCabinPeriodToAvailablePeriodDto(AvailableCabinPeriod availableCabinPeriod){
        return new AvailablePeriodDto(availableCabinPeriod.getId(),availableCabinPeriod.getStartDate(),
                availableCabinPeriod.getEndDate(),availableCabinPeriod.getCabinOwner().getUsername(),availableCabinPeriod.getCabin().getId());
    }
    public  AvailableCabinPeriod availablePeriodDtoToAvailableCabinPeriod(AvailablePeriodDto availablePeriodDto, CabinOwner cabinOwner, Cabin cabin){
        return  new AvailableCabinPeriod(availablePeriodDto.getId(), availablePeriodDto.getStartDate(), availablePeriodDto.getEndDate(),cabinOwner,cabin);
    }
}
