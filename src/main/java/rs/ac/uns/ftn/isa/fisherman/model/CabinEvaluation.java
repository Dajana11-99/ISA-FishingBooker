package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CabinEvaluation extends Evaluation{

    @ManyToOne
    @JoinColumn(name= "cabin_reservation_id")
    CabinReservation cabinReservation;

    public CabinEvaluation(Long id, LocalDateTime date, String comment, Double grade, boolean approved, Client client, CabinReservation cabinReservation) {
        super(id, date, comment, grade, approved, client);
        this.cabinReservation = cabinReservation;
    }

    public CabinEvaluation() {}

    public CabinReservation getCabinReservation() {
        return cabinReservation;
    }

    public void setCabinReservation(CabinReservation cabinReservation) {
        this.cabinReservation = cabinReservation;
    }
}
