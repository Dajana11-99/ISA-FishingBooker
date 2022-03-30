package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;

public class BoatReservationMapper {
    private  BoatMapper boatMapper=new BoatMapper();
    private AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();

    public BoatReservation boatReservationOwnerDtoToBoatReservation(BoatReservationDto boatReservationDto){
        Boat boat=boatMapper.boatDtoToBoat(boatReservationDto.getBoatDto());
        return new BoatReservation(boatReservationDto.getId(),boatReservationDto.getStartDate(),
                boatReservationDto.getEndDate(),boatReservationDto.getPrice(),null,boat,
                additionalServiceMapper.additionalServicesDtoToAdditionalServices(boatReservationDto.getAddedAdditionalServices()),
                boatReservationDto.getNeedsCaptainServices());
    }
    public BoatReservationDto boatReservationToBoatReservationDto(BoatReservation boatReservation){
        String fullName=boatReservation.getClient().getName()+" "+boatReservation.getClient().getLastName();
        return new BoatReservationDto(boatReservation.getId(),boatReservation.getStartDate(),
                boatReservation.getEndDate(),boatReservation.getPrice(),boatReservation.getClient().getUsername(),
                fullName,null,null,boatReservation.getNeedsCaptainService());
    }

}
