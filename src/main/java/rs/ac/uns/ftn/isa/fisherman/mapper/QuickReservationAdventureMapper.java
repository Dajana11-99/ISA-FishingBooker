package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdventureReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationAdventureDto;
import rs.ac.uns.ftn.isa.fisherman.model.Client;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;

public class QuickReservationAdventureMapper {
    private AdditionalServiceMapper additionalServiceMapper = new AdditionalServiceMapper();
    private AdventureMapper adventureMapper = new AdventureMapper();
    public QuickReservationAdventure dtoToQuickReservationAdventure(QuickReservationAdventureDto quickReservationAdventureDto, FishingInstructor fishingInstructor){
        return new QuickReservationAdventure(quickReservationAdventureDto.getId(),quickReservationAdventureDto.getStartDate(),quickReservationAdventureDto.getEndDate(),
           quickReservationAdventureDto.getPrice(),adventureMapper.adventureDtoToAdventure(quickReservationAdventureDto.getAdventureDto()),
                fishingInstructor,additionalServiceMapper.additionalServicesDtoToAdditionalServices(quickReservationAdventureDto.getAddedAdditionalServices()),quickReservationAdventureDto.getDiscount());
    }


    public QuickReservationAdventureDto quickAdventureReservationToQuickAdventureReservationDto(QuickReservationAdventure quickReservationAdventure) {
        Client client=new Client();
        String fullName=null;
        if(quickReservationAdventure.getClient() !=null){
            client.setUsername(quickReservationAdventure.getClient().getUsername());
            fullName=quickReservationAdventure.getClient().getName()+" "+quickReservationAdventure.getClient().getLastName();
        }

        return new QuickReservationAdventureDto(quickReservationAdventure.getId(),quickReservationAdventure.getStartDate(),
                quickReservationAdventure.getEndDate(),quickReservationAdventure.getPrice(), client.getUsername(), fullName,adventureMapper.adventureToAdventureDto(
                quickReservationAdventure.getAdventure()),additionalServiceMapper
                .additionalServicesToAdditionalServiceDtoS(quickReservationAdventure.getAddedAdditionalServices())
,quickReservationAdventure.getDiscount());
    }
}
