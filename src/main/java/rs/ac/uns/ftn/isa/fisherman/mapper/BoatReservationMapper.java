package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;


public class BoatReservationMapper {
    private final BoatMapper boatMapper=new BoatMapper();
    private final AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();
    private final PaymentInformationMapper paymentInformationMapper=new PaymentInformationMapper();

    public BoatReservation boatReservationOwnerDtoToBoatReservation(BoatReservationDto boatReservationDto){
        Boat boat=boatMapper.boatDtoToBoat(boatReservationDto.getBoatDto());
        return new BoatReservation(boatReservationDto.getId(),boatReservationDto.getStartDate(),
                boatReservationDto.getEndDate(),null,paymentInformationMapper.dtoToPaymentInformation(boatReservationDto.getPaymentInformationDto()),
                boatReservationDto.isOwnerWroteAReport(),boatReservationDto.getOwnersUsername(),boat,
                additionalServiceMapper.additionalServicesDtoToAdditionalServices(boatReservationDto.getAddedAdditionalServices()),
                boatReservationDto.getNeedsCaptainServices());
    }
    public BoatReservationDto boatReservationToBoatReservationDto(BoatReservation boatReservation){
        String fullName=boatReservation.getClient().getName()+" "+boatReservation.getClient().getLastName();
        return new BoatReservationDto(boatReservation.getId(),boatReservation.getStartDate(),
                boatReservation.getEndDate(),boatReservation.getClient().getUsername(),
                fullName,paymentInformationMapper.paymentInformationToDto(boatReservation.getPaymentInformation()),boatReservation.isSuccessfull(),
                boatReservation.isOwnerWroteAReport(),boatReservation.getOwnersUsername(),
                boatMapper.boatToBoatDto(boatReservation.getBoat()),
                additionalServiceMapper.additionalServicesToAdditionalServiceDtoS(boatReservation.getAddedAdditionalServices()),
                boatReservation.getNeedsCaptainService(), boatReservation.isEvaluated());
    }
    public BoatReservation boatReservationDtoToBoatReservation(BoatReservationDto boatReservationDto){
        return new BoatReservation(boatReservationDto.getId(),boatReservationDto.getStartDate(),
                boatReservationDto.getEndDate(),null,paymentInformationMapper.dtoToPaymentInformation(boatReservationDto.getPaymentInformationDto()),
                boatReservationDto.isOwnerWroteAReport(),boatReservationDto.getOwnersUsername(),boatMapper.boatDtoToBoat(boatReservationDto.getBoatDto()),
                null,
                boatReservationDto.getNeedsCaptainServices());
    }

}
