package rs.ac.uns.ftn.isa.fisherman.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class BoatReservationDto extends ReservationDto{
    private BoatDto boatDto;
    private Set<AdditionalServicesDto> addedAdditionalServices;
    private boolean needsCaptainServices;
    public BoatReservationDto(){}

    public BoatReservationDto(Long id, LocalDateTime startDate, LocalDateTime endDate, String clientUsername, String clientFullName, PaymentInformationDto paymentInformationDto,boolean successfull,boolean ownerWroteAReport, String ownersUsername,BoatDto boatDto, Set<AdditionalServicesDto> addedAdditionalServices, boolean needsCaptainServices, boolean evaluated) {
        super(id, startDate, endDate, clientUsername, clientFullName, paymentInformationDto,successfull,ownerWroteAReport,ownersUsername, evaluated);
        this.boatDto = boatDto;
        this.addedAdditionalServices = addedAdditionalServices;
        this.needsCaptainServices = needsCaptainServices;
    }

    public BoatDto getBoatDto() {
        return boatDto;
    }

    public void setBoatDto(BoatDto boatDto) {
        this.boatDto = boatDto;
    }

    public Set<AdditionalServicesDto> getAddedAdditionalServices() {
        return addedAdditionalServices;
    }

    public void setAddedAdditionalServices(Set<AdditionalServicesDto> addedAdditionalServices) {
        this.addedAdditionalServices = addedAdditionalServices;
    }

    public boolean getNeedsCaptainServices() {
        return needsCaptainServices;
    }

    public void setNeedsCaptainServices(boolean needsCaptainServices) {
        this.needsCaptainServices = needsCaptainServices;
    }
}
