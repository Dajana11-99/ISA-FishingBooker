package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdditionalServicesDto;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;

import java.util.HashSet;
import java.util.Set;

public class CabinReservationMapper {

    private final CabinMapper cabinMapper = new CabinMapper();
    private final AdditionalServiceMapper additionalServicesMapper=new AdditionalServiceMapper();
    public CabinReservation cabinReservationDtoToCabinReservation(CabinReservationDto cabinReservationDto){
        Cabin cabin = cabinMapper.cabinDtoToCabin(cabinReservationDto.getCabinDto());
        return new CabinReservation(cabinReservationDto.getId(),
                cabinReservationDto.getStartDate(),cabinReservationDto.getEndDate(), cabinReservationDto.getPrice(),
                new Client(), cabin, null);

    }
    public CabinReservation cabinOwnerReservationDtoToCabinReservation(CabinReservationDto cabinReservationDto){
        Cabin cabin = cabinMapper.cabinDtoToCabin(cabinReservationDto.getCabinDto());
        return new CabinReservation(cabinReservationDto.getId(),
                cabinReservationDto.getStartDate(),cabinReservationDto.getEndDate(), cabinReservationDto.getPrice(),
                new Client(), cabin, additionalServicesMapper.additionalServicesDtoToAdditionalServices(cabinReservationDto.getAddedAdditionalServices()));

    }

    public CabinReservationDto cabinReservationToCabinReservationDto(CabinReservation cabinReservation){
        String fullName=cabinReservation.getClient().getName()+" "+cabinReservation.getClient().getLastName();
        Set<AdditionalServicesDto> additionalServicesDtos=new HashSet<>();
        if(cabinReservation.getAddedAdditionalServices()!=null)
             additionalServicesDtos=additionalServicesMapper.additionalServicesToAdditionalServiceDtoS(cabinReservation.getAddedAdditionalServices());
        return new CabinReservationDto(cabinReservation.getId(),cabinReservation.getStartDate(),
                cabinReservation.getEndDate(),cabinReservation.getPrice(),cabinReservation.getClient().getUsername(),
                fullName,cabinMapper.cabinToCabinDto(cabinReservation.getCabin()),additionalServicesDtos);
    }

}
