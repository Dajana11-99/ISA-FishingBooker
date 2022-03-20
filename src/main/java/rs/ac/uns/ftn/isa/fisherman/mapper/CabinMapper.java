package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.CabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;

public class CabinMapper {
    private final AddressMapper addressMapper=new AddressMapper();
    private final AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();


    public Cabin CabinDtoToCabin(CabinDto cabin){
        return new Cabin(cabin.getId(),cabin.getName(),cabin.getDescription(),cabin.getNumOfRooms(),cabin.getBedsPerRoom(),
                cabin.getRules(),cabin.getPrice(),addressMapper.dtoToAddress(cabin.getAddressDto()),
                cabin.getRating());

    }
    public Cabin CabinDtoEditToCabin(CabinDto cabin){
        return new Cabin(cabin.getId(), cabin.getName(), cabin.getDescription(), cabin.getNumOfRooms(),
                cabin.getBedsPerRoom(), cabin.getRules(), cabin.getPrice(),
                addressMapper.dtoToAddress(cabin.getAddressDto()),
                additionalServiceMapper.AdditionalServicesDtoToAdditionalServices(cabin.getAdditionalServices()),
                cabin.getRating());
    }
    public CabinDto CabinToCabinDto(Cabin cabin){
        ImageMapper imageMapper=new ImageMapper();
        AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();
        return new CabinDto(cabin.getId(),cabin.getName(),cabin.getDescription(),cabin.getNumOfRooms(),cabin.getBedsPerRoom(),
                cabin.getRules(),cabin.getPrice(),addressMapper.addressToDTO(cabin.getAddress()),
                additionalServiceMapper.AdditionalServicesToAdditionalServiceDtos(cabin.getAdditionalServices()),
                cabin.getRating(),imageMapper.ImageToImageDtoS(cabin.getImages()),cabin.getCabinOwner().getUsername());
    }
}
