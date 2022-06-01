package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdventureSubscriptionDto;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureSubscription;

public class AdventureSubscriptionMapper {
    private final AdventureMapper adventureMapper = new AdventureMapper();
    public AdventureSubscriptionDto adventureSubscriptionToAdventureSubscriptionDto(AdventureSubscription adventureSubscription) {
        return new AdventureSubscriptionDto(adventureSubscription.getId(), adventureSubscription.getClient().getUsername(), adventureMapper.adventureToAdventureDto(adventureSubscription.getAdventure()));
    }
}
