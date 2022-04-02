package rs.ac.uns.ftn.isa.fisherman.dto;

import java.time.LocalDateTime;

public class CabinEvaluationDto extends EvaluationDto{
    private CabinReservationDto cabinReservationDto;

    public CabinEvaluationDto(Long id, LocalDateTime date, String comment, Double grade, boolean approved, String clientUsername, CabinReservationDto cabinReservationDto) {
        super(id, date, comment, grade, approved, clientUsername);
        this.cabinReservationDto = cabinReservationDto;
    }

    public CabinEvaluationDto() {}

    public CabinReservationDto getCabinReservationDto() {
        return cabinReservationDto;
    }

    public void setCabinReservationDto(CabinReservationDto cabinReservationDto) {
        this.cabinReservationDto = cabinReservationDto;
    }
}
