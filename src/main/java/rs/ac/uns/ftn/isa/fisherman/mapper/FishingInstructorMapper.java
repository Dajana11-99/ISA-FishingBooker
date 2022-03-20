package rs.ac.uns.ftn.isa.fisherman.mapper;
import rs.ac.uns.ftn.isa.fisherman.dto.FishingInstructorDto;
import rs.ac.uns.ftn.isa.fisherman.dto.UserRequestDTO;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;

public class FishingInstructorMapper {
    private final AddressMapper addressMapper = new AddressMapper();
    private final AvailableInstructorPeriodMapper availableInstructorPeriodMapper = new AvailableInstructorPeriodMapper();

    public FishingInstructor userRequestDtoToFishingInstructor(UserRequestDTO userRequest){
        return new FishingInstructor(userRequest.getId(),userRequest.getFirstname(),userRequest.getLastname(),userRequest.getUsername(),
                userRequest.getPassword(),userRequest.getPhoneNum(),addressMapper.dtoToAddress(userRequest.getAddress()),userRequest.getRegistrationReason());
    }

    public UserRequestDTO fishingInstructorToUserRequestDto(FishingInstructor fishingInstructor){
        return new UserRequestDTO(fishingInstructor.getUsername(),fishingInstructor.getName(),fishingInstructor.getLastName(),fishingInstructor.getRoleApp(),fishingInstructor.getRegistrationReason());
    }

    public FishingInstructorDto fishingInstructorToFishingInstructorDto(FishingInstructor fishingInstructor){
        return  new FishingInstructorDto(fishingInstructor.getId(),fishingInstructor.getUsername(),fishingInstructor.getPassword(),fishingInstructor.getName(),
                fishingInstructor.getLastName(),fishingInstructor.getPhoneNum(),addressMapper.addressToDTO(fishingInstructor.getAddress()),
                fishingInstructor.getRegistrationReason(),fishingInstructor.getRoleApp(),fishingInstructor.getRating(), availableInstructorPeriodMapper.availableInstructorPeriodsToDtoS(fishingInstructor.getAvailableInstructorPeriods()));
    }


}
