package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdventureReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;

public class AdventureReservationMapper {
    private final AdventureMapper adventureMapper= new AdventureMapper();
    private final AdditionalServiceMapper additionalServiceMapper = new AdditionalServiceMapper();
    private final PaymentInformationMapper paymentInformationMapper=new PaymentInformationMapper();
    public AdventureReservation adventureReservationDtoToAdventureReservation(AdventureReservationDto adventureReservationDto,FishingInstructor fishingInstructor){
       Adventure adventure = adventureMapper.adventureDtoToAdventure(adventureReservationDto.getAdventureDto());
        return new AdventureReservation(adventureReservationDto.getId(),adventureReservationDto.getStartDate(),
                adventureReservationDto.getEndDate(),null,paymentInformationMapper.dtoToPaymentInformation(adventureReservationDto.getPaymentInformationDto()),
                adventureReservationDto.isOwnerWroteAReport(),adventureReservationDto.getOwnersUsername(),
                adventure,fishingInstructor,
                additionalServiceMapper.additionalServicesDtoToAdditionalServices(adventureReservationDto.getAddedAdditionalServices()));
    }
    public AdventureReservationDto adventureReservationToAdventureReservationDto(AdventureReservation adventureReservation){
        String fullName=adventureReservation.getClient().getName()+" "+adventureReservation.getClient().getLastName();
        return new AdventureReservationDto(adventureReservation.getId(),
                adventureReservation.getStartDate(),adventureReservation.getEndDate(),adventureReservation.getClient().getUsername(),fullName,
                paymentInformationMapper.paymentInformationToDto(adventureReservation.getPaymentInformation()),adventureReservation.isSuccessfull(),
                adventureReservation.isOwnerWroteAReport(),adventureReservation.getOwnersUsername(),
                adventureMapper.adventureToAdventureDto(adventureReservation.getAdventure()),additionalServiceMapper
                .additionalServicesToAdditionalServiceDtoS(adventureReservation.getAddedAdditionalServices()), adventureReservation.isEvaluated());
    }


}
