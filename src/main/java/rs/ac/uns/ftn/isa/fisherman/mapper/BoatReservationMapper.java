package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;


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
                fullName,boatMapper.boatToBoatDto(boatReservation.getBoat()),
                additionalServiceMapper.additionalServicesToAdditionalServiceDtoS(boatReservation.getAddedAdditionalServices()),
                boatReservation.getNeedsCaptainService());
    }
    public BoatReservationDto quickBoatReservationToBoatReservationDto(QuickReservationBoat quickReservationBoat) {
        Client client=new Client();
        String fullName=null;
        if(quickReservationBoat.getClient() !=null){
            client.setUsername(quickReservationBoat.getClient().getUsername());
            fullName=quickReservationBoat.getClient().getName()+" "+quickReservationBoat.getClient().getLastName();
        }

        return new BoatReservationDto(quickReservationBoat.getId(),quickReservationBoat.getStartDate(),
                quickReservationBoat.getEndDate(),quickReservationBoat.getPrice(),client.getUsername(),
                fullName,boatMapper.boatToBoatDto(quickReservationBoat.getBoat()),
                additionalServiceMapper.additionalServicesToAdditionalServiceDtoS(quickReservationBoat.getAddedAdditionalServices()),
                quickReservationBoat.getNeedsCaptainServices());
    }

}
