package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.CabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;

public class CabinMapper {
    private final AddressMapper addressMapper=new AddressMapper();
    private final AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();

    public Cabin cabinDtoToCabin(CabinDto cabin){
        return new Cabin(cabin.getId(),cabin.getName(),cabin.getDescription(),cabin.getNumOfRooms(),cabin.getBedsPerRoom(),
                cabin.getRules(),cabin.getPrice(),addressMapper.dtoToAddress(cabin.getAddressDto()),
                cabin.getRating());

    }
    public Cabin cabinDtoEditToCabin(CabinDto cabin){
        return new Cabin(cabin.getId(), cabin.getName(), cabin.getDescription(), cabin.getNumOfRooms(),
                cabin.getBedsPerRoom(), cabin.getRules(), cabin.getPrice(),
                addressMapper.dtoToAddress(cabin.getAddressDto()),
                additionalServiceMapper.additionalServicesDtoToAdditionalServices(cabin.getAdditionalServices()),
                cabin.getRating());
    }
    public CabinDto cabinToCabinDto(Cabin cabin){
        ImageMapper imageMapper=new ImageMapper();
        return new CabinDto(cabin.getId(),cabin.getName(),cabin.getDescription(),cabin.getNumOfRooms(),cabin.getBedsPerRoom(),
                cabin.getRules(),cabin.getPrice(),addressMapper.addressToDTO(cabin.getAddress()),
                additionalServiceMapper.additionalServicesToAdditionalServiceDtoS(cabin.getAdditionalServices()),
                cabin.getRating(),imageMapper.imageToImageDtoS(cabin.getImages()),cabin.getCabinOwner().getUsername());
    }
}
