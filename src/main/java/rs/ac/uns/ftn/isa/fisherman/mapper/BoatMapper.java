package rs.ac.uns.ftn.isa.fisherman.mapper;
import rs.ac.uns.ftn.isa.fisherman.dto.BoatDto;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;

public class BoatMapper {
    private AddressMapper addressMapper=new AddressMapper();
    private ImageMapper imageMapper=new ImageMapper();
    private AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();

    public Boat boatDtoToBoat(BoatDto boat){
        return new Boat(boat.getId(),boat.getName(),boat.getType(),boat.getLength(), boat.getEngineCode(), boat.getEnginePower(),
                boat.getMaxSpeed(), boat.getNavigationEquipment(), addressMapper.dtotoaddress(boat.getAddressDto()),boat.getDescription(),
                boat.getMaxPeople(), boat.getRules(), boat.getFishingEquipment(), boat.getPrice(), boat.getRating(), boat.getCancelingCondition());
    }

    public BoatDto boatToBoatDto(Boat boat) {
        return new BoatDto(boat.getId(),boat.getBoatOwner().getUsername(),boat.getName(), boat.getType(), boat.getLength(), boat.getEngineCode(),
                boat.getEnginePower(),boat.getMaxSpeed(),boat.getNavigationEquipment(),addressMapper.adressToDTO(boat.getAddress()),
                boat.getDescription(),imageMapper.ImageToImageDtos(boat.getImages()),boat.getMaxPeople(),boat.getRules(),
                boat.getFishingEquipment(),boat.getPrice(),additionalServiceMapper.AdditionalServicesToAdditionalServiceDtos(boat.getAdditionalServices()),
                boat.getCancelingCondition(), boat.getRating());
    }
}
