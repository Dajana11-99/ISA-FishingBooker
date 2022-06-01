package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.AdditionalServicesDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationBoatDto;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;

import java.util.HashSet;
import java.util.Set;

public class QuickReservationBoatMapper {
    private final BoatMapper boatMapper=new BoatMapper();
    private final AdditionalServiceMapper additionalServiceMapper=new AdditionalServiceMapper();
    private final PaymentInformationMapper paymentInformationMapper=new PaymentInformationMapper();
    public QuickReservationBoat dtoToBoatQuickReservation(QuickReservationBoatDto quickReservationBoatDto) {
        Set<AdditionalServices> additionalServicesSet=new HashSet<>();
        if(quickReservationBoatDto.getAddedAdditionalServices()!=null)
            additionalServicesSet=additionalServiceMapper.additionalServicesDtoToAdditionalServices(quickReservationBoatDto.getAddedAdditionalServices());
        return  new QuickReservationBoat(quickReservationBoatDto.getId(),quickReservationBoatDto.getStartDate(),
                quickReservationBoatDto.getEndDate(),null,paymentInformationMapper.dtoToPaymentInformation(quickReservationBoatDto.getPaymentInformationDto()),
              quickReservationBoatDto.isOwnerWroteAReport(),quickReservationBoatDto.getOwnersUsername(),boatMapper.boatDtoToBoat(quickReservationBoatDto.getBoatDto())
                ,quickReservationBoatDto.getDiscount(),additionalServicesSet,quickReservationBoatDto.getNeedsCaptainServices());
    }
    public QuickReservationBoatDto boatQuickReservationToDto(QuickReservationBoat quickReservationBoat) {
        Set<AdditionalServicesDto> additionalServicesSet=new HashSet<>();
        if(quickReservationBoat.getAddedAdditionalServices()!=null)
            additionalServicesSet=additionalServiceMapper.additionalServicesToAdditionalServiceDtoS(quickReservationBoat.getAddedAdditionalServices());
        String clientUsername="";
        String clientFullName="";
        if(quickReservationBoat.getClient()!=null){
            clientUsername=quickReservationBoat.getClient().getUsername();
            clientFullName=quickReservationBoat.getClient().getName()+" "+quickReservationBoat.getClient().getLastName();
        }
        return  new QuickReservationBoatDto(quickReservationBoat.getId(),quickReservationBoat.getStartDate(),
                quickReservationBoat.getEndDate(),clientUsername,clientFullName,paymentInformationMapper
                .paymentInformationToDto(quickReservationBoat.getPaymentInformation()),quickReservationBoat.isSuccessfull(),
                 quickReservationBoat.isOwnerWroteAReport(),quickReservationBoat.getOwnersUsername(),
                boatMapper.boatToBoatDto(quickReservationBoat.getBoat()),additionalServicesSet,
                quickReservationBoat.getNeedsCaptainServices(),quickReservationBoat.getDiscount(), quickReservationBoat.isEvaluated());
    }
}
