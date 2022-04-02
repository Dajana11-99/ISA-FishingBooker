package rs.ac.uns.ftn.isa.fisherman.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class AvailableCabinPeriod extends AvailablePeriod {
    @ManyToOne
    @JoinColumn(name="users_id")
    protected CabinOwner cabinOwner;

    @ManyToOne
    @JoinColumn(name="cabin_id")
    protected Cabin cabin;

    public AvailableCabinPeriod(){}

    public AvailableCabinPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate, CabinOwner cabinOwner, Cabin cabin) {
        super(id, startDate, endDate);
        this.cabinOwner = cabinOwner;
        this.cabin = cabin;
    }

    public CabinOwner getCabinOwner() {
        return cabinOwner;
    }

    public void setCabinOwner(CabinOwner cabinOwner) {
        this.cabinOwner = cabinOwner;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }
}
