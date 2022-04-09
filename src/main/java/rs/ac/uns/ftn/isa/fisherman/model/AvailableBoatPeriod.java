package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity

@DiscriminatorValue("BoatPeriod")
public class AvailableBoatPeriod extends AvailablePeriod {

    @ManyToOne
    @JoinColumn(name="boat_id")
    protected Boat boat;

    public AvailableBoatPeriod(){}

    public AvailableBoatPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate, Boat boat) {
        super(id, startDate, endDate);
        this.boat = boat;
    }


    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
