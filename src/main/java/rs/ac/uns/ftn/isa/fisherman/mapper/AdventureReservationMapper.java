package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdventureReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;

public class AdventureReservationMapper {
    private AdventureMapper adventureMapper= new AdventureMapper();
    private AdditionalServiceMapper additionalServiceMapper = new AdditionalServiceMapper();
    private PaymentInformationMapper paymentInformationMapper=new PaymentInformationMapper();

    public AdventureReservation adventureReservationDtoToAdventureReservation(AdventureReservationDto adventureReservationDto,FishingInstructor fishingInstructor){
       Adventure adventure = adventureMapper.adventureDtoToAdventure(adventureReservationDto.getAdventureDto());
        return new AdventureReservation(adventureReservationDto.getId(),adventureReservationDto.getStartDate(),
                adventureReservationDto.getEndDate(),null,paymentInformationMapper.dtoToPaymentInformation(adventureReservationDto.getPaymentInformationDto()),adventure,fishingInstructor,
                additionalServiceMapper.additionalServicesDtoToAdditionalServices(adventureReservationDto.getAddedAdditionalServices()));
    }
    public AdventureReservationDto adventureReservationToAdventureReservationDto(AdventureReservation adventureReservation){
        String fullName=adventureReservation.getClient().getName()+" "+adventureReservation.getClient().getLastName();
        return new AdventureReservationDto(adventureReservation.getId(),
                adventureReservation.getStartDate(),adventureReservation.getEndDate(),adventureReservation.getClient().getUsername(),fullName,
                paymentInformationMapper.paymentInformationToDto(adventureReservation.getPaymentInformation()),adventureReservation.isSuccessfull(),
                adventureMapper.adventureToAdventureDto(adventureReservation.getAdventure()),additionalServiceMapper
                .additionalServicesToAdditionalServiceDtoS(adventureReservation.getAddedAdditionalServices()));
    }


}
