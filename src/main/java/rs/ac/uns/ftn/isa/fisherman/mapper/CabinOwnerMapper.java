package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;

public class CabinOwnerMapper {

    public CabinOwner userRequestDTOToCabinOwner(UserRequestDTO userRequest) {
        AddressMapper addressMapper=new AddressMapper();
       return new CabinOwner(userRequest.getId(),userRequest.getFirstname(),userRequest.getLastname(),userRequest.getUsername(),
                userRequest.getPassword(),userRequest.getPhoneNum(),addressMapper.dtoToAddress(userRequest.getAddress()),userRequest.getRegistrationReason());
    }

}
