package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.CabinOwnerDTO;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;

public class CabinOwnerMapper {

    public CabinOwnerDTO cabinOwnerToDTO(CabinOwner cabinOwner){
        return new CabinOwnerDTO(cabinOwner.getId(), cabinOwner.getName(), cabinOwner.getLastName(), cabinOwner.getEmail(),
                cabinOwner.getPassword(), cabinOwner.getPhoneNum(), cabinOwner.getAddress(), cabinOwner.getRegistrationReason());
    }
    public CabinOwner dtotocabinowner(CabinOwnerDTO cabinOwnerDTO){
        return new CabinOwner(cabinOwnerDTO.getId(), cabinOwnerDTO.getName(), cabinOwnerDTO.getLastName(), cabinOwnerDTO.getEmail(),
                cabinOwnerDTO.getPassword(), cabinOwnerDTO.getPhoneNum(), cabinOwnerDTO.getAddress(), cabinOwnerDTO.getRegistrationReason());
    }

    public CabinOwner userRequestDTOToCabinOwner(UserRequestDTO userRequest) {
        AddressMapper addressMapper=new AddressMapper();


       CabinOwner cabinOwner = new CabinOwner(userRequest.getId(),userRequest.getFirstname(),userRequest.getLastname(),userRequest.getEmail(),
                userRequest.getPassword(),userRequest.getPhoneNum(),addressMapper.dtotoaddress(userRequest.getAddress()),userRequest.getRegistrationReason());
        System.out.println("AAAAAAAAAAAAAAAAAAAAA"+  cabinOwner.getPassword());
        return cabinOwner;
    }
}
